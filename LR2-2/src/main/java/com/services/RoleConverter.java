package com.services;

import com.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<String, Role> {
   @Override
   public Role convert(String source) {
      return Role.fromName(source);
   }
}
