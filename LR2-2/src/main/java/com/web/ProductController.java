package com.web;

import com.model.Tuple;
import com.repositories.AccountRepository;
import com.repositories.ContextRunner;
import com.repositories.ProductRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Objects;

@Controller
@RequestMapping({"/products"})
public class ProductController {
   private final ProductRepository productRepository;
   private final AccountRepository accountRepository;
   private final ContextRunner contextRunner;

   public ProductController(ProductRepository productRepository, AccountRepository accountRepository, ContextRunner contextRunner) {
      this.productRepository = productRepository;
      this.accountRepository = accountRepository;
      this.contextRunner = contextRunner;
   }

   @GetMapping
   public String products(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(name = "offset", defaultValue = "0") @Min(0) int offset,
                          @RequestParam(name = "limit", defaultValue = "10") @Min(10) @Max(100) int limit,
                          Model model) {
      if(userDetails == null) {
         model.addAttribute("isLogin", false);
      } else {
         model.addAttribute("isLogin", true);
         model.addAttribute("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
      }
      var products = contextRunner.run(productRepository.list(offset, limit));
      model.addAttribute("products", products);

      model.addAttribute("offset", offset);
      model.addAttribute("limit", limit);
      return "products";
   }

   @PostMapping("buy/{productName}")
   public String buy(@AuthenticationPrincipal UserDetails userDetails,
                     @PathVariable(name = "productName") @NotEmpty String name,
                     @RequestParam(name = "quantity", defaultValue = "1") @Min(1) int quantity) {
      var user = userDetails.getUsername();
      contextRunner.run(c -> {
         var acc = accountRepository.findByName(user).run(c);
         if (acc == null) throw new IllegalStateException("Аккаунт не найден");
         var prod = productRepository.findByName(name).run(c);
         if (prod == null) throw new IllegalStateException("Продукт не найден");
         if (prod.quantity < quantity) throw new IllegalStateException("Мало товара");
         var total = prod.cost.multiply(BigDecimal.valueOf(quantity));
         if (acc.amount.compareTo(total) < 0) throw new IllegalStateException("Мало денег");
         var accSeller = accountRepository.findById(prod.account.id).run(c);
         if (accSeller == null) throw new IllegalStateException("Продавец не найден");
         
         prod.quantity -= quantity;
         acc.amount = acc.amount.subtract(total);
         accSeller.amount = accSeller.amount.add(total);
         productRepository.save(prod).run(c);
         accountRepository.save(acc).run(c);
         accountRepository.save(accSeller).run(c);
         return null;
      });

      return "redirect:/products";
   }
}
