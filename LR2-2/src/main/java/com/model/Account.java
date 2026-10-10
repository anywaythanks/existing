package com.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Account {
   public Long id;
   @NotNull
   @Size(min = 5, max = 16, message = "{login.size}")
   public String name;
   @NotNull
   @Size(min = 5, max = 16, message = "{login.size}")
   public String passwd;
   @NotNull
   public Role role;
   public BigDecimal amount;

   public Account(String name, String passwd, Role role) {
      this.name = name;
      this.passwd = passwd;
      this.role = role;
      amount = BigDecimal.ZERO;
   }
   public Account() {
      this.name = "";
      this.passwd = "";
      this.role = Role.BUYER;
      amount = BigDecimal.ZERO;
   }
   @Override
   public String toString() {
      return "Account{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", passwd='" + passwd + '\'' +
              ", role=" + role +
              ", amount=" + amount +
              '}';
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPasswd() {
      return passwd;
   }

   public void setPasswd(String passwd) {
      this.passwd = passwd;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public void setAmount(BigDecimal amount) {
      this.amount = amount;
   }
}
