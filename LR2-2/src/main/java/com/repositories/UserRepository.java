package com.repositories;

import com.model.User;

public interface UserRepository {
    User save(User user);

    User findByLogin(String login);
}
