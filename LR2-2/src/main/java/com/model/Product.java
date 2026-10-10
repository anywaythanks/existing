package com.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Product {
   public Long id;
   @NotNull
   @Size(min = 3, max = 40)
   public String name;
   @NotNull @Size(min = 1, max = 40)
   public String visibleName;
   @NotNull @DecimalMin("0.01")
   public BigDecimal cost;
   @Min(0)
   public int quantity;
   public Account account;
   public String description;

   public Product(String name, Account account, String visibleName, String description, BigDecimal cost) {
      this.name = name;
      this.account = account;
      this.visibleName = visibleName;
      this.description = description;
      this.cost = cost;
      quantity = 0;
   }

   public Product() {
      this.id = null;
      this.name ="";
      this.account = new Account();
      this.visibleName ="";
      this.description = "";
      this.cost = BigDecimal.ZERO;
      this.quantity = 0;
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

   public Account getAccount() {
      return account;
   }

   public void setAccount(Account account) {
      this.account = account;
   }

   public String getVisibleName() {
      return visibleName;
   }

   public void setVisibleName(String visibleName) {
      this.visibleName = visibleName;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public BigDecimal getCost() {
      return cost;
   }

   public void setCost(BigDecimal cost) {
      this.cost = cost;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
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
