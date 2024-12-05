package repositories;

import model.Account;
import model.Role;

import javax.sql.DataSource;

public class AccountRepositoryJdbc implements AccountRepository {
   protected final JdbcTemplate<Account> jdbcTemplate;

   public AccountRepositoryJdbc(DataSource dataSource) {
      this.jdbcTemplate = new JdbcTemplate<>(dataSource, (account, preparedStatement) -> {
         preparedStatement.setString(1, account.name);
         preparedStatement.setString(2, account.passwd);
         preparedStatement.setBigDecimal(3, account.amount);
         preparedStatement.setLong(4, account.id);
      }, (id, account, preparedStatement) -> {
         account.id = id;
         System.out.println("account.id: " + account.id);
         preparedStatement.setLong(1, account.id);
         preparedStatement.setString(2, account.name);
         preparedStatement.setString(3, account.passwd);
         preparedStatement.setString(4, account.role.getName());
         preparedStatement.setBigDecimal(5, account.amount);
      }, resultSet -> {
         var name = resultSet.getString("name");
         var passwd = resultSet.getString("password");
         var role = resultSet.getString("role");
         var acc = new Account(name, passwd, Role.valueOf(role.toUpperCase()));
         acc.id = resultSet.getLong("id");
         return acc;
      }, "account_seq");
   }

   public Account save(Account account) {
      if (account.id == null) {
         return jdbcTemplate.insert("INSERT INTO accounts (id, name, password, role, amount) VALUES (?, ?, ?, ?, ?)", account);
      }
      return jdbcTemplate.run("update accounts set name=?, password=?, amount=? where id=?", account);
   }

   public boolean delete(Account account) {
      return jdbcTemplate.run("delete from accounts where id=?", account, (a, p) -> p.setLong(1, a.id)) != null;
   }

   public Account findByName(String name) {
      return jdbcTemplate.select("select * from accounts where name=?", ps -> ps.setString(1, name));
   }

   public Account findById(long id) {
      return jdbcTemplate.select("select * from accounts where id=?", ps -> ps.setLong(1, id));
   }
}
