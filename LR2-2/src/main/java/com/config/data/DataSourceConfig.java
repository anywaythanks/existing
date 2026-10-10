package com.config.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
   @Bean
   public DataSource dataSource() {
      HikariConfig config = new HikariConfig();

      config.setDriverClassName("org.postgresql.Driver");
      config.setJdbcUrl("jdbc:postgresql://postgres:5432/applec?charSet=UTF-8");
      config.setUsername("anyway");
      config.setPassword("anyway");

      config.setMaximumPoolSize(15);
      config.setMinimumIdle(5);
      config.setIdleTimeout(300000);
      config.setConnectionTimeout(20000);
      config.setPoolName("MyCustomHikariPool");

      return new HikariDataSource(config);
   }
}
