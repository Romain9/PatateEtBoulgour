package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.example.PatateEtBoulgour.services.AddressService;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @Autowired
    private AddressService addressService;

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        // Récupération des erreurs si existantes.
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMessages.add(fieldError.getDefaultMessage()));
        bindingResult.getGlobalErrors().forEach(globalError -> errorMessages.add(globalError.getDefaultMessage()));
        model.addAttribute("errors", errorMessages);
        model.addAttribute("user", user);

        // Pas d'erreur, vérification de l'addresse via l'API du gouvernement
        if (!bindingResult.hasErrors()) {
            try {
                addressService.getCoordinates(user.getAddress());
            } catch (InvalidApiResponse e) {
                errorMessages.add("Un incident est arrivé. Veuillez ré-essayer plus tard");
            } catch (InvalidAddressException e) {
                errorMessages.add("Cette addresse est introuvable");
            }
        }

        // Renvoie vers le formulaire avec les erreurs
        if (!errorMessages.isEmpty()) {
            return "forms/newUserForm";
        }

        // Enregistrement de l'utilisateur
        userService.createUser(user);
        return "redirect:/admin/user-list";

    }

    @GetMapping("/createUser")
    public String createUser() {
        return "forms/newUserForm";
    }
}
