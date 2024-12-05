package repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.Properties;

public class DataSourceFactory {
   private DataSource dataSource;

   public DataSourceFactory() {
      Properties props = new Properties();

      props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
      props.setProperty("dataSource.user", "anyway");
      props.setProperty("dataSource.databaseName", "applec");

      HikariConfig config = new HikariConfig(props);
      this.dataSource = new HikariDataSource(config);
   }

   public DataSource dataSource() {
      return dataSource;
   }
}
