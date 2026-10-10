package com.config;

import com.repositories.AccountRepository;
import com.repositories.ContextRunner;
import com.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   private final AccountRepository repository;
   private final ContextRunner contextRunner;

   public SecurityConfig(AccountRepository repository, ContextRunner contextRunner) {
      this.repository = repository;
      this.contextRunner = contextRunner;
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(new UserService(repository, contextRunner));
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/", "/homepage", "/messages", "/register", "/login").permitAll()
              .antMatchers("/profile/{name}").access("isAuthenticated() and principal.username == #name")
              .anyRequest().authenticated()
              .and().formLogin().loginPage("/login").permitAll()
              .and().httpBasic().realmName("Simple")
              .and().rememberMe().tokenValiditySeconds(2419200).key("remember-me")
              .and().logout().logoutSuccessUrl("/").logoutUrl("/signout")
              .and().csrf().disable();
   }
}
