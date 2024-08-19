package com.chikacow.pet_project.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        return "security/login";
    }

    @GetMapping("/logout")
    public String getLogoutForm(Model model) {
        return "security/logout";
    }
}
