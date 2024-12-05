package repositories;

import model.Account;
import model.Product;
import model.Role;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRepositoryTest {
   @Autowired
   ProductRepository repo;
   @Autowired
   AccountRepository accRepo;
   static Account account1;
   static Product product1;

   @BeforeClass
   public static void setUp() {
      account1 = new Account("test2", "test2", Role.SALESMAN);
      product1 = new Product("test", account1, "visName", "descr", new BigDecimal("10.00"));
   }

   protected static void eq(Product product1, Product product2) {
      assertEquals(product1.name, product2.name);
      assertEquals(product1.visibleName, product2.visibleName);
      assertEquals(product1.quantity, product2.quantity);
      assertEquals(product1.cost, product2.cost);
      assertEquals(product1.description, product2.description);
      AccountRepositoryTest.eq(product1.account, product2.account);
   }

   @Test
   public void _1_nullFind() {
      var find = accRepo.findByName(account1.name);
      if (find != null) accRepo.delete(find);
      assertNull(accRepo.findByName(account1.name));
      assertNull(repo.findByName(product1.name));
   }

   @Test
   public void _2_save() {
      assertNull(product1.id);
      account1 = accRepo.save(account1);
      assertNotNull(account1.id);
      var saved = repo.save(product1);
      assertNotNull(saved.id);
   }

   @Test
   public void _3_findByName() {
      var find = repo.findByName(product1.name);
      assertNotNull(find.id);
      eq(find, product1);
   }

   @Test
   public void _4_findById() {
      var find1 = repo.findByName(product1.name);
      var find2 = repo.findById(find1.id);
      assertNotNull(find2.id);
      eq(find2, find1);
      eq(find2, product1);
   }

   @Test
   public void _5_delete() {
      var find = repo.findByName(product1.name);
      assertNotNull(find);
      repo.delete(find);
      find = repo.findByName(product1.name);
      assertNull(find);
   }
}