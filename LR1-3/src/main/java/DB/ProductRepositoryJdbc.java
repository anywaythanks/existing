package DB;

import DB.entities.Account;
import DB.entities.Product;
import DB.entities.Role;

import javax.sql.DataSource;

public class ProductRepositoryJdbc implements ProductRepository {
   protected final JdbcTemplate<Product> jdbcTemplate;

   public ProductRepositoryJdbc(DataSource dataSource, AccountRepositoryJdbc accountRepositoryJdbc) {
      var aj = accountRepositoryJdbc.jdbcTemplate;
      this.jdbcTemplate = new JdbcTemplate<>(dataSource, (product, preparedStatement) -> {
         preparedStatement.setString(0, product.name);
         preparedStatement.setString(1, product.visibleName);
         preparedStatement.setString(2, product.description);
         preparedStatement.setBigDecimal(3, product.cost);
         preparedStatement.setInt(4, product.quantity);
         preparedStatement.setLong(5, product.id);
      }, (id, product, preparedStatement) -> {
         product.id = id;
         System.out.println("product.id: " + product.id);
         preparedStatement.setLong(0, product.id);
         preparedStatement.setString(1, product.name);
         preparedStatement.setString(2, product.visibleName);
         preparedStatement.setString(3, product.description);
         preparedStatement.setBigDecimal(4, product.cost);
         preparedStatement.setInt(5, product.quantity);
      }, resultSet -> {
         var name = resultSet.getString("name");
         var visibleName = resultSet.getString("visible_name");
         var description = resultSet.getString("description");
         var cost = resultSet.getBigDecimal("cost");
         var quantity = resultSet.getInt("quantity");
         var accountId = resultSet.getInt("account_id");

         Account account = aj.exec(c -> aj.select("select * from accounts where id=?", ps -> ps.setLong(0, accountId)));

         var product = new Product(name, null, visibleName, description, cost);
         product.quantity = quantity;
         product.account = account;
         product.id = resultSet.getLong("id");
         return product;
      }, "account_seq");
   }

   public Product save(Product account) {
      if (account.id == null) {
         return jdbcTemplate.insert("INSERT INTO products (id, name, visible_name, description, cost, quantity) VALUES (?, ?, ?, ?, ?);", account);
      }
      return jdbcTemplate.run("update products set name=?, visible_name=?, description=?, cost=?, quantity=? where id=?", account);
   }

   public boolean delete(Product product) {
      return jdbcTemplate.run("delete from products where id=?", product, (a, p) -> p.setLong(0, a.id)) != null;
   }

   public Product findByName(String name) {
      return jdbcTemplate.select("select * from products where name=?", ps -> ps.setString(0, name));
   }

   public Product findById(long id) {
      return jdbcTemplate.select("select * from products where id=?", ps -> ps.setLong(0, id));
   }
}
