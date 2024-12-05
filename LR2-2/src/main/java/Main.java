import model.Account;
import model.Product;
import model.Role;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.AccountRepository;
import repositories.ProductRepositoryJdbc;

import java.util.Scanner;
import java.util.function.Supplier;

public class Main {

   static <T> T read(String input, Supplier<? extends T> supplier) {
      System.out.print(input);
      return supplier.get();
   }

   public static void main(String[] args) {
      try (var context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
         var accRep = context.getBean(AccountRepository.class);
         var productRep = context.getBean(ProductRepositoryJdbc.class);

         Scanner in = new Scanner(System.in);
         loop:
         while (true) {
            System.out.println("""
                    1 - создать аккаунт
                    2 - создать продукт
                    3 - прочитать аккаунт по имени
                    4 - прочитать продукт по имени
                    otherwise - выход.
                    """);
            switch (in.nextInt()) {
               case 1 -> {
                  Account account = new Account(read("Имя:", in::next),
                          read("Пароль:", in::next), Role.SALESMAN);
                  accRep.save(account);
               }
               case 2 -> {
                  Account account = accRep.findByName(read("Имя аккаунта:", in::next));
                  Product product = new Product(read("Имя продукта:", in::next), account,
                          read("Видимое имя продукта:", in::next), "",
                          read("Стоимость:", in::nextBigDecimal));
                  productRep.save(product);
               }
               case 3 -> {
                  Account account = accRep.findByName(read("Имя аккаунта:", in::next));
                  System.out.println(account);
               }
               case 4 -> {
                  Product product = productRep.findByName(read("Имя продукта:", in::next));
                  System.out.println(product);
               }
               default -> {
                  break loop;
               }
            }
         }
      }
   }
}
