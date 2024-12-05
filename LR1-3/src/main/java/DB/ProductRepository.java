package DB;

import DB.entities.Account;
import DB.entities.Product;

import javax.sql.DataSource;

public interface ProductRepository {
   Product save(Product account);

   boolean delete(Product product);

   Product findByName(String name);

   Product findById(long id);
}
