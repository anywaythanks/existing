package com.web;

import com.repositories.AccountRepository;
import com.repositories.ContextRunner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
   private final AccountRepository accountRepository;
   private final ContextRunner contextRunner;

   public ProfileController(AccountRepository accountRepository, ContextRunner contextRunner) {
      this.accountRepository = accountRepository;
      this.contextRunner = contextRunner;
   }

   @GetMapping("/{name}")
   public String profile(@PathVariable String name, Model model) {
      var account = contextRunner.run(accountRepository.findByName(name));
      if (account == null) {
         return "redirect:/products";
      }
      model.addAttribute("account", account);
      return "profile";
   }

   @PostMapping("/{name}/topup")
   public String topup(@AuthenticationPrincipal UserDetails userDetails,
                       @PathVariable String name,
                       @RequestParam("amount") BigDecimal amount) {
      if (userDetails == null || !userDetails.getUsername().equals(name)) {
         return "redirect:/products";
      }
      if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
         return "redirect:/profile/" + name;
      }
      contextRunner.run(c -> {
         var acc = accountRepository.findByName(name).run(c);
         if (acc == null) throw new IllegalStateException("Аккаунт не найден");
         acc.amount = acc.amount.add(amount);
         accountRepository.save(acc).run(c);
         return null;
      });
      return "redirect:/profile/" + name;
   }
}