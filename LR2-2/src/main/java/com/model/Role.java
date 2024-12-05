package com.model;

public enum Role {
   SALESMAN, BUYER;

   public String getName() {
      return name().toLowerCase();
   }
}
