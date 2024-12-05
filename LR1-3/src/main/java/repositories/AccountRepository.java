package repositories;

import model.Account;

public interface AccountRepository {
   Account save(Account account);

   boolean delete(Account account);

   Account findByName(String name);

   Account findById(long id);
}
