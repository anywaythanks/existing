package com.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.util.TagUtils.SCOPE_REQUEST;

@Controller
@RequestMapping({"/login"})
@Scope(SCOPE_REQUEST)
public class LoginController {
    @RequestMapping(method = GET)
    public String messages() {
        return "loginForm";
    }
}
