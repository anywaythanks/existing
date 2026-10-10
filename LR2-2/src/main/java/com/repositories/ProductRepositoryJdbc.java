package com.repositories;

import com.model.Account;
import com.model.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductRepositoryJdbc implements ProductRepository {
   protected final JdbcTemplate<Product> jdbcTemplate;

   public ProductRepositoryJdbc(AccountRepositoryJdbc accountRepositoryJdbc) {
      this.jdbcTemplate = new JdbcTemplate<>((product, preparedStatement) -> {
         preparedStatement.setString(1, product.name);
         preparedStatement.setString(2, product.visibleName);
         preparedStatement.setString(3, product.description);
         preparedStatement.setBigDecimal(4, product.cost);
         preparedStatement.setInt(5, product.quantity);
         preparedStatement.setLong(6, product.id);
      }, (id, product, preparedStatement) -> {
         product.id = id;
         System.out.println("product.id: " + product.id);
         preparedStatement.setLong(1, product.id);
         preparedStatement.setString(2, product.name);
         preparedStatement.setString(3, product.visibleName);
         preparedStatement.setString(4, product.description);
         preparedStatement.setBigDecimal(5, product.cost);
         preparedStatement.setInt(6, product.quantity);
         preparedStatement.setLong(7, product.account.id);
      }, resultSet -> {
         var name = resultSet.getString("name");
         var visibleName = resultSet.getString("visible_name");
         var description = resultSet.getString("description");
         var cost = resultSet.getBigDecimal("cost");
         var quantity = resultSet.getInt("quantity");
         var accountId = resultSet.getLong("account_id");

         var product = new Product(name, null, visibleName, description, cost);
         product.quantity = quantity;
         product.account = new Account();
         product.account.id = accountId;
         product.id = resultSet.getLong("id");
         return product;
      }, "account_seq");
   }

   public ContextJdbc<Product> save(Product account) {
      if(account.id == null) {
         return jdbcTemplate.insert("INSERT INTO products (id, name, visible_name, description, cost, quantity, account_id) VALUES (?, ?, ?, ?, ?, ?, ?)", account);
      }
      return jdbcTemplate.run("update products set name=?, visible_name=?, description=?, cost=?, quantity=? where id=?", account);
   }

   public ContextJdbc<Boolean> delete(Product product) {
      return jdbcTemplate.run("delete from products where id=?", product, (a, p) -> p.setLong(1, a.id)).map(Objects::nonNull);
   }

   public ContextJdbc<Product> findByName(String name) {
      return jdbcTemplate.selectOne("select * from products where name=?", ps -> ps.setString(1, name));
   }

   public ContextJdbc<Product> findById(long id) {
      return jdbcTemplate.selectOne("select * from products where id=?", ps -> ps.setLong(1, id));
   }

   @Override
   public ContextJdbc<List<Product>> list(int offset, int limit) {
      return jdbcTemplate.select("select * from products limit ? offset ?", ps -> {
         ps.setInt(1, limit);
         ps.setInt(2, offset);
      });
   }

   @Override
   public ContextJdbc<List<Product>> list(int offset, int limit, long accountId) {
      return jdbcTemplate.select("select * from products where account_id = ? limit ? offset ? ", ps -> {
         ps.setLong(1, accountId);
         ps.setInt(2, limit);
         ps.setInt(3, offset);
      });
   }
}
