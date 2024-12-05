package DB;

import DB.entities.Account;

public interface AccountRepository {
   Account save(Account account);

   boolean delete(Account account);

   Account findByName(String name);

   Account findById(long id);
}
