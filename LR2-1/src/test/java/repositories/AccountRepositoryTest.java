package repositories;

import model.Account;
import model.Role;
import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.apache.log4j.BasicConfigurator.configure;
import static org.apache.log4j.Logger.getRootLogger;
import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountRepositoryTest {
   static AccountRepository repo;
   static Account account1;

   @BeforeClass
   public static void setUp() {
      configure();
      getRootLogger().setLevel(Level.INFO);
      repo = new AccountRepositoryJdbc(new DataSourceFactory().dataSource());
      account1 = new Account("test", "test", Role.SALESMAN);
   }

   protected static void eq(Account account1, Account account2) {
      assertEquals(account1.name, account2.name);
      assertEquals(account1.passwd, account2.passwd);
      assertEquals(account1.role, account2.role);
      assertEquals(account1.amount, account2.amount);
   }
   @Test
   public void _1_nullFind() {
      assertNull(repo.findByName(account1.name));
   }

   @Test
   public void _2_save() {
      assertNull(account1.id);
      var saved = repo.save(account1);
      assertNotNull(saved.id);
   }

   @Test
   public void _3_findByName() {
      var find = repo.findByName(account1.name);
      assertNotNull(find.id);
      eq(find, account1);
   }

   @Test
   public void _4_findById() {
      var find1 = repo.findByName(account1.name);
      var find2 = repo.findById(find1.id);
      assertNotNull(find2.id);
      eq(find2, find1);
      eq(find2, account1);
   }

   @Test
   public void _5_delete() {
      var find = repo.findByName(account1.name);
      assertNotNull(find);
      repo.delete(find);
      find = repo.findByName(account1.name);
      assertNull(find);
   }
}