package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            // Si des erreurs de validation sont présentes, renvoyer le formulaire avec les valeurs saisies et les erreurs
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(fieldError -> errorMessages.add(fieldError.getDefaultMessage()));
            bindingResult.getGlobalErrors().forEach(globalError -> errorMessages.add(globalError.getDefaultMessage()));
            model.addAttribute("errors", errorMessages);

            // renvoie des données à la vue.
            model.addAttribute("user", user);

            return "forms/newUserForm";
        } else {

            // Créer l'objet User avec les données du formulaire
            // Ajouter les coordonnées GPS et l'adresse à l'utilisateur
            userService.createUser(user);
            return "redirect:/admin/user-list";
        }

    }

    @GetMapping("/createUser")
    public String createUser() {
        return "forms/newUserForm";
    }
}
