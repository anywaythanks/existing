package DB.entities;

import java.math.BigDecimal;

public class Account {
   public Long id;
   public String name;
   public String passwd;
   public Role role;
   public BigDecimal amount;

   public Account(String name, String passwd, Role role) {
      this.name = name;
      this.passwd = passwd;
      this.role = role;
      amount = BigDecimal.ZERO;
   }
}
