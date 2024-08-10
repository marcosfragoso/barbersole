package com.edu.ifpb.barbersole.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/index"})
    public String index(HttpServletResponse response) {
        return "index";
    }

    @GetMapping({"/register"})
    public String register(HttpServletResponse response) {
        return "register";
    }

    @GetMapping({"/login"})
    public String login(HttpServletResponse response) {
        return "login";
    }
}
