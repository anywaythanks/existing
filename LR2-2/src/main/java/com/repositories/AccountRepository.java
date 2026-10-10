package com.repositories;

import com.model.Account;

public interface AccountRepository {
   ContextJdbc<Account> save(Account account);

   ContextJdbc<Boolean> delete(Account account);

   ContextJdbc<Account> findByName(String name);

   ContextJdbc<Account> findById(long id);
}
