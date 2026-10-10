package com.web;

import com.model.Account;
import com.model.Product;
import com.repositories.AccountRepository;
import com.repositories.ContextRunner;
import com.repositories.ProductRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping({"/manage"})
public class ManageController {
   private final ProductRepository productRepository;
   private final AccountRepository accountRepository;
   private final ContextRunner contextRunner;

   public ManageController(ProductRepository productRepository, AccountRepository accountRepository, ContextRunner contextRunner) {
      this.productRepository = productRepository;
      this.accountRepository = accountRepository;
      this.contextRunner = contextRunner;
   }

   @RequestMapping(method = GET)
   public String manage(@AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam(name = "offset", defaultValue = "0") @Min(0) int offset,
                        @RequestParam(name = "limit", defaultValue = "10") @Min(10) @Max(100) int limit,
                        Model model) {
      if(userDetails == null) {
         return "redirect:/login";
      } else {
         if(!userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("salesman")) {
            return "redirect:/products";
         }
      }
      var acc = accountRepository.findByName(userDetails.getUsername());
      var products = contextRunner.run(acc.map(Account::getId).flatMap(accountId -> productRepository.list(offset, limit, accountId)));
      model.addAttribute("products", products);
      model.addAttribute("product", new Product());
      return "manage";
   }

   @PostMapping("delivery/{productName}")
   public String deliveryProducts(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable(name = "productName") @NotEmpty String name,
                                  @RequestParam(name = "quantity", defaultValue = "1") @Min(1) int quantity) {
      if(userDetails == null) {
         return "redirect:/login";
      } else {
         if(!userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("salesman")) {
            return "redirect:/manage";
         }
      }
      var user = userDetails.getUsername();
      contextRunner.run(c -> {
         var acc = accountRepository.findByName(user).run(c);
         if(acc == null) throw new IllegalStateException("Аккаунт не найден");
         var prod = productRepository.findByName(name).run(c);
         if(prod == null) throw new IllegalStateException("Продукт не найден");
         prod.quantity += quantity;
         productRepository.save(prod).run(c);
         return null;
      });

      return "redirect:/manage";
   }

   @PostMapping("create")
   public String createProducts(@AuthenticationPrincipal UserDetails userDetails,
                                @Valid Product product, Errors errors, Model model
   ) {
      if(userDetails == null) {
         return "redirect:/login";
      } else {
         if(!userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("salesman")) {
            return "redirect:/manage";
         }
      }
      if(errors.hasErrors()) {
         return "redirect:/manage";
      }
      model.addAttribute("product", product);
      contextRunner.run(c -> {
         var acc = accountRepository.findByName(userDetails.getUsername()).run(c);
         if(acc == null) throw new IllegalStateException("Аккаунт не найден");
         var prod = productRepository.findByName(product.name).run(c);
         if(prod != null) throw new IllegalStateException("Продукт уже существует");
         product.account = acc;
         productRepository.save(product).run(c);
         return null;
      });

      return "redirect:/manage";
   }
}
