package com.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface ContextJdbc<T> {
   T run(Connection c) throws Exception;

   default <V> ContextJdbc<V> map(Function<? super T, ? extends V> f) {
      return c -> f.apply(run(c));
   }

   default <V> ContextJdbc<V> flatMap(Function<? super T, ? extends ContextJdbc<V>> f) {
      return c -> f.apply(run(c)).run(c);
   }

   default <V, U> ContextJdbc<U> concat(ContextJdbc<V> val, BiFunction<? super T, ? super V, ? extends U> f) {
      return this.flatMap(t -> val.map(v -> f.apply(t, v)));
   }

   default ContextJdbc<T> check(Predicate<T> p, Exception e){
      return c -> {
        var v = this.run(c);
        if(p.test(v)){
           throw e;
        }
        return v;
      };
   }
}
