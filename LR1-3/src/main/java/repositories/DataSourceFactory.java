package repositories;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
   DataSource dataSource;

   public DataSourceFactory() {
      var dataSource = new HikariDataSource();
      dataSource.setDriverClassName("com.postgresql.Driver");
      dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/applec");
      dataSource.setUsername("anyway");
      this.dataSource = dataSource;
   }

   private DataSource dataSource() {
      return dataSource;
   }
}
