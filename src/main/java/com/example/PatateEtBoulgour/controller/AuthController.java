package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/process")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
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
            model.addAttribute("error", "Echec de authentication");
            model.addAttribute("username", username);
            return "forms/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidating the session
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "forms/login";
    }
}
