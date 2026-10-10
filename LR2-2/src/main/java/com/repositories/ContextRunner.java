package com.repositories;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;

@Service
public class ContextRunner {
   private final DataSource dataSource;

   public ContextRunner(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   public <T> T run(ContextJdbc<T> context) {
      try(var c = dataSource.getConnection()) {
         c.setAutoCommit(false);
         try {
            var t = context.run(c);
            c.commit();
            return t;
         } catch(Exception e) {
            c.rollback();
            throw new RuntimeException(e);
         }
      } catch(SQLException e) {
         throw new RuntimeException(e);
      }
   }
}
