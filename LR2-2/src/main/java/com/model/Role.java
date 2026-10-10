package com.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Role {
   SALESMAN, BUYER;
   public static final Map<Role, String> ROLE_MAP = Arrays.stream(Role.values()).collect(Collectors.toMap(Function.identity(), Role::getName));
   public static final Map<String, Role> NAMES_MAP = Arrays.stream(Role.values()).collect(Collectors.toMap(Role::getName, Function.identity()));
   public static final String[] NAMES = ROLE_MAP.values().toArray(new String[0]);

   public String getName() {
      return name().toLowerCase();
   }

   public static Role fromName(String name) {
      return NAMES_MAP.get(name);
   }
}
