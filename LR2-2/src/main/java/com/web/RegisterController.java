package com.web;

import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import com.repositories.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserRepository repository;

    public RegisterController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute(new User());
        return "registerForm";
    }

    @PostMapping
    public String processRegistration(@RequestPart("profilePicture") byte[] profilePicture,
                                      @Valid User user, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "registerForm";
        }
        model.addAttribute("login", user.getLogin());
        return "redirect:/{username}";
    }
}
