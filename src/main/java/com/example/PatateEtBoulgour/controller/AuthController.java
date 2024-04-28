package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/process")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model, RedirectAttributes ra) {
        // Vérification des informations d'identification de l'utilisateur
        User user = userService.authenticate(username, password);
        if (user != null) {
            // Création de la session et stockage des informations de l'utilisateur
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getUsername());
            // Redirection vers une page de succès
            return "redirect:/";
        } else {
            // Redirection vers la page de login
            ra.addFlashAttribute("error", "Echec de authentication");
            ra.addFlashAttribute("username", username);

            return "redirect:/login";
        }
    }

    @RequireLogged
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidation de la session
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "forms/login";
    }
}
