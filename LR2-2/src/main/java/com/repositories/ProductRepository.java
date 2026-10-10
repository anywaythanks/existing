package com.repositories;

import com.model.Product;

import java.util.List;

public interface ProductRepository {
   ContextJdbc<Product> save(Product account);

   ContextJdbc<Boolean> delete(Product product);

   ContextJdbc<Product> findByName(String name);

   ContextJdbc<Product> findById(long id);

   ContextJdbc<List<Product>> list(int offset, int limit);
   ContextJdbc<List<Product>> list(int offset, int limit, long accountId);
}
