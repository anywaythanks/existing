package com.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BIGINT;

/*
 * В изначальных версиях был изъян - невозможность сделать красиво транзакции. В оригинальном темплейете это реализовано через AOP и тем фактом, что jdbcTemplate существует в единственном экземпляре. Мы поступим иначе, погружая иные вызовы в контекст, который разрешит кто-то наверху.
 * Таким образом контекст будет тянуться ровно до того момента, пока кому-то не понадобится транзакция и все будет связано одним коннектом. В этом случае dataSource нам более не нужен.
 * Целью не является сделать какие-то сложные вещи типа неблокирующих операций.
 * Цель остается той же - это просто обертка над jdbc, ни больше ни меньше. Мы не будем заменять sql своим языком, не будем на лету его генерировать. Все отдается на откуп jdbc. Мы просто убираем повторяющиеся бойлерплейтные элементы.
 */
public class JdbcTemplate<T> {

   private BiConsumer<? super T, ? super PreparedStatement> updateAction;
   private IdBiConsumer<? super T, ? super PreparedStatement> insertAction;
   private Function<? super ResultSet, ? extends T> mapper;
   private String seqName;

   @FunctionalInterface
   public interface IdBiConsumer<T, P> {
      void accept(Long id, T t, P p) throws SQLException;
   }

   @FunctionalInterface
   public interface BiConsumer<T, P> {
      void accept(T t, P p) throws SQLException;
   }

   @FunctionalInterface
   public interface Consumer<P> {
      void accept(P p) throws SQLException;
   }

   @FunctionalInterface
   public interface Function<T, U> {
      U apply(T t) throws SQLException;
   }

   public JdbcTemplate() {
   }

   public JdbcTemplate(BiConsumer<? super T, ? super PreparedStatement> updateAction, IdBiConsumer<? super T, ? super PreparedStatement> insertAction, Function<? super ResultSet, ? extends T> mapper, String seqName) {
      this.updateAction = updateAction;
      this.insertAction = insertAction;
      this.mapper = mapper;
      this.seqName = seqName;
   }

   public ContextJdbc<T> run(String query, T updateObject) {
      return c -> run(c, query, updateObject, updateAction);
   }

   public ContextJdbc<T> run(String query, T updateObject, BiConsumer<? super T, ? super PreparedStatement> updateAction) {
      return c -> run(c, query, updateObject, updateAction);
   }
   public ContextJdbc<T> selectOne(String query, Consumer<? super PreparedStatement> selectValues) {
      return c -> selectOne(c, query, selectValues, mapper);
   }

   public ContextJdbc<List<T>> select(String query, Consumer<? super PreparedStatement> selectValues) {
      return c -> select(c, query, selectValues, mapper);
   }

   public List<T> select(Connection c, String query, Consumer<? super PreparedStatement> selectValues,
                         Function<? super ResultSet, ? extends T> mapper)  throws SQLException {
      try(var ps = c.prepareStatement(query)) {
         selectValues.accept(ps);
         var rs = ps.executeQuery();
         ArrayList<T> result = new ArrayList<>();
         while(rs.next()) {
            result.add(mapper.apply(rs));
         }
         return result;
      }
   }

   /**
    * вставляет используя секвенс
    */
   public ContextJdbc<T> insert(String query, T insertObject) {
      return c -> insert(c, query, insertObject);
   }

   public T insert(Connection c, String query, T insertObject) throws SQLException {
      try(var call = c.prepareCall("{call nextval(?::regclass)}");
          var ps = c.prepareStatement(query)) {
         call.setString(1, seqName);
         call.registerOutParameter(1, BIGINT);
         call.execute();
         long id = call.getLong(1);
         insertAction.accept(id, insertObject, ps);
         return ps.executeUpdate() > 0 ? insertObject : null;
      }
   }

   public T run(Connection c, String query, T updateObject,
                BiConsumer<? super T, ? super PreparedStatement> mapper)  throws SQLException {
      try(var ps = c.prepareStatement(query)) {
         mapper.accept(updateObject, ps);
         return ps.executeUpdate() > 0 ? updateObject : null;
      }
   }

   public T selectOne(Connection c, String query, Consumer<? super PreparedStatement> selectValues,
                      Function<? super ResultSet, ? extends T> mapper) throws SQLException  {
      try(var ps = c.prepareStatement(query)) {
         selectValues.accept(ps);
         var rs = ps.executeQuery();
         return rs.next() ? mapper.apply(rs) : null;
      }
   }

   public <V> ContextJdbc<V> exec(Function<Connection, ? extends V> action) {
         return action::apply;
   }
}
