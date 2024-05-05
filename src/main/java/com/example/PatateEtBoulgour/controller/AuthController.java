package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.entities.Pathology;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.example.PatateEtBoulgour.repository.PathologyRepository;
import com.example.PatateEtBoulgour.services.AddressService;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PathologyRepository pathologyRepository;

    @Autowired
    private AddressService addressService;

    @PostMapping("/login/process")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes ra) {
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

    @PostMapping("/register")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, RedirectAttributes ra) {

        // Récupération des erreurs si existantes.
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMessages.add(fieldError.getDefaultMessage()));
        bindingResult.getGlobalErrors().forEach(globalError -> errorMessages.add(globalError.getDefaultMessage()));

        model.addAttribute("errors", errorMessages);
        model.addAttribute("user", user);
        model.addAttribute("pathology", user.getPathology());

        List<Pathology> allPathologies = pathologyRepository.findAll();
        model.addAttribute("allPathologies", allPathologies);

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
            List<Pathology> pathologies = pathologyRepository.findAll();
            model.addAttribute("pathologies", pathologies);
            return "forms/newUserForm";
        }

        userService.createUser(user);
        ra.addFlashAttribute("success", "Utilisateur créé avec succès! Veuillez vous connecter");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String createUser(Model model) {
        List<Pathology> pathologies = pathologyRepository.findAll();
        model.addAttribute("pathologies", pathologies);

        return "forms/newUserForm";
    }

}
