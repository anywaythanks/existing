package com.services;

import com.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = repository.findByLogin(login);
        if (user != null) {
            List<GrantedAuthority> authorities =
                    new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return org.springframework.security.core.userdetails.User
                    .withDefaultPasswordEncoder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        }
        throw new UsernameNotFoundException("User '" + login + "' not found.");
    }


}