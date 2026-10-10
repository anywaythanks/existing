package com.services;

import com.model.Account;
import com.repositories.AccountRepository;
import com.repositories.ContextRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {
   private final AccountRepository repository;
   private final ContextRunner contextRunner;

   public UserService(AccountRepository repository, ContextRunner contextRunner) {
      this.repository = repository;
      this.contextRunner = contextRunner;
   }

   @Override
   public UserDetails loadUserByUsername(String login)
           throws UsernameNotFoundException {
      Account user = contextRunner.run(repository.findByName(login));
      if(user != null) {
         List<GrantedAuthority> authorities =
                 new ArrayList<>();
         authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
         return org.springframework.security.core.userdetails.User
                 .withDefaultPasswordEncoder()
                 .username(user.name)
                 .password(user.passwd)
                 .authorities(authorities)
                 .build();
      }
      throw new UsernameNotFoundException("User '" + login + "' not found.");
   }


}