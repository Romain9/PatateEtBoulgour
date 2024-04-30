package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.example.PatateEtBoulgour.services.ActivityService;
import com.example.PatateEtBoulgour.services.AddressService;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private ActivityService activityService;

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

    @RequireLogged
    @RequestMapping("/user")
    public ModelAndView account() {
        Map<String, Object> m = new HashMap<>();

        ModelAndView mv = new ModelAndView("profile");

        User user = userService.getCurrentUser();
        if (user != null){
            m.put("user", user);

            Set<Activity> activities = userService.getUserActivities(user);

            mv.addObject("user", user);
            mv.addObject("activities", activities);
            m.put("activities", activities);
        }

        return new ModelAndView("profile", m);
    }

    @GetMapping("add{activityId}")
    public String addActivity(@PathVariable("activityId") Long activityId) {
        User user = userService.getCurrentUser();
        activityService.addUserToActivity(user, activityId);
        return "redirect:/";
    }
}
