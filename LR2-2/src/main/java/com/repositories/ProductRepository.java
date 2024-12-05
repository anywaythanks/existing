package com.repositories;

import com.model.Product;

public interface ProductRepository {
   Product save(Product account);

   boolean delete(Product product);

   Product findByName(String name);

   Product findById(long id);
}
