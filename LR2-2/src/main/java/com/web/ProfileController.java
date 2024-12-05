package com.web;

import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.repositories.UserRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/{login}", method = GET)
public class ProfileController {
    private final UserRepository repository;

    public ProfileController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String showProfile(
            @PathVariable String login, Model model) {
        User user = repository.findByLogin(login);
        model.addAttribute(user);
        return "profile";
    }
}
