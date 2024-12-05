package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.repositories.UserRepository;
import com.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserRepository repository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserService(repository));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/homepage", "/messages", "/register").permitAll()
                .antMatchers("/{login}")
                .access("isAuthenticated() and principal.username == #login")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().httpBasic().realmName("Simple")
                .and().requiresChannel().antMatchers("/register").requiresSecure()
                .and().rememberMe().tokenValiditySeconds(2419200).key("remember-me")
                .and().logout().logoutSuccessUrl("/").logoutUrl("/signout");
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
