package repositories;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate<T> {
   private final DataSource dataSource;
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

   public JdbcTemplate(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   public JdbcTemplate(DataSource dataSource, BiConsumer<? super T, ? super PreparedStatement> updateAction, IdBiConsumer<? super T, ? super PreparedStatement> insertAction, Function<? super ResultSet, ? extends T> mapper, String seqName) {
      this.dataSource = dataSource;
      this.updateAction = updateAction;
      this.insertAction = insertAction;
      this.mapper = mapper;
      this.seqName = seqName;
   }

   public T run(String query, T updateObject) {
      return run(query, updateObject, updateAction);
   }

   public T select(String query, Consumer<? super PreparedStatement> selectValues) {
      return select(query, selectValues, mapper);
   }

   /**
    * вставляет используя секвенс
    */
   public T insert(String query, T insertObject) {
      try (var c = dataSource.getConnection()) {
         return insert(c, query, insertObject);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public T run(String query, T updateObject, BiConsumer<? super T, ? super PreparedStatement> mapper) {
      try {
         var c = dataSource.getConnection();
         return run(c, query, updateObject, mapper);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public T select(String query, Consumer<? super PreparedStatement> selectValues, Function<? super ResultSet, ? extends T> mapper) {
      try {
         var c = dataSource.getConnection();
         return select(c, query, selectValues, mapper);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public T insert(Connection c, String query, T insertObject) {
      try (var call = c.prepareCall("nextval('%s'::regclass)".formatted(seqName));
           var ps = c.prepareStatement(query)) {
         long id = call.executeQuery().getLong(0);
         insertAction.accept(id, insertObject, ps);
         return ps.executeUpdate() > 0 ? insertObject : null;
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public T run(Connection c, String query, T updateObject,
                BiConsumer<? super T, ? super PreparedStatement> mapper) {
      try (var ps = c.prepareStatement(query)) {
         mapper.accept(updateObject, ps);
         return ps.executeUpdate() > 0 ? updateObject : null;
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public T select(Connection c, String query, Consumer<? super PreparedStatement> selectValues,
                   Function<? super ResultSet, ? extends T> mapper) {
      try (var ps = c.prepareStatement(query)) {
         selectValues.accept(ps);
         return mapper.apply(ps.executeQuery());
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public <V> V exec(Function<Connection, ? extends V> action) {
      try {
         var c = dataSource.getConnection();
         return action.apply(c);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }
}
