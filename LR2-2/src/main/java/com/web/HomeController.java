package com.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping({"/", "/homepage"})
public class HomeController {
    @RequestMapping(method = GET)
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if(userDetails == null){
            model.addAttribute("isLogin", false);
        }else {
            model.addAttribute("isLogin", true);
            model.addAttribute("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        }
        return "home";
    }
}
