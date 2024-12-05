package com.model;

import java.math.BigDecimal;

public class Product {
   public Long id;
   public String name;
   public Account account;
   public String visibleName;
   public String description;
   public BigDecimal cost;
   public int quantity;

   public Product(String name, Account account, String visibleName, String description, BigDecimal cost) {
      this.name = name;
      this.account = account;
      this.visibleName = visibleName;
      this.description = description;
      this.cost = cost;
      quantity = 0;
   }

   @Override
   public String toString() {
      return "Product{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", account=" + account +
              ", visibleName='" + visibleName + '\'' +
              ", description='" + description + '\'' +
              ", cost=" + cost +
              ", quantity=" + quantity +
              '}';
   }
}
