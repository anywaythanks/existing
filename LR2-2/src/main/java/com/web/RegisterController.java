package com.web;

import com.model.Account;
import com.repositories.AccountRepository;
import com.repositories.ContextRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
   private final AccountRepository repository;
   private final ContextRunner contextRunner;

   public RegisterController(AccountRepository repository, ContextRunner contextRunner) {
      this.repository = repository;
      this.contextRunner = contextRunner;
   }

   @GetMapping
   public String showRegistrationForm(Model model) {
      model.addAttribute(new Account());
      return "registerForm";
   }

   @PostMapping
   public String processRegistration(@Valid Account user, Errors errors, Model model) {
      if(errors.hasErrors()) {
         return "registerForm";
      }
      contextRunner.run(repository.save(user));
      model.addAttribute("login", user.name);
      return "redirect:/profile/{login}";
   }
}
