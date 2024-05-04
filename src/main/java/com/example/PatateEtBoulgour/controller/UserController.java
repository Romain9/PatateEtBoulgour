package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.dto.Coordonnees;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.Parcours;
import com.example.PatateEtBoulgour.entities.Pathology;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import com.example.PatateEtBoulgour.repository.ParcoursRepository;
import com.example.PatateEtBoulgour.services.AddressService;
import com.example.PatateEtBoulgour.services.DistanceService;
import com.example.PatateEtBoulgour.services.RecommandationService;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RecommandationService recommandationService;

    @Autowired
    private ParcoursRepository parcoursRepository;

    @RequireLogged
    @RequestMapping("/user")
    public ModelAndView account() {
        ModelAndView mv = new ModelAndView("profile");
        User user = userService.getCurrentUser();
        List<Activity> activities = user.getActivities();

        mv.addObject("user", user);

        for (Activity activity : activities) {
            activity.setContainsCurrentUser(true);
        }


        if (userService.isLoggedIn()) {
            mv.addObject("activitiesCarroussel",
                    recommandationService.recommandFor(userService.getCurrentUser())
            );
        }

        mv.addObject("inProfile", true);

        mv.addObject("activities", activities);

        return mv;
    }

    @RequireLogged
    @PostMapping("/note/parcours")
    public String noteParcours(@Valid @ModelAttribute("parcours") Parcours parcours, BindingResult bindingResult, Model m, RedirectAttributes ra) {
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMessages.add(fieldError.getDefaultMessage()));
        bindingResult.getGlobalErrors().forEach(globalError -> errorMessages.add(globalError.getDefaultMessage()));

        // Renvoie vers le formulaire avec les erreurs
        if (!errorMessages.isEmpty()) {
            ra.addFlashAttribute("errors", errorMessages);
            return "redirect:/user";
        }

        User user = userService.getCurrentUser();

        parcours.setActivities(new ArrayList<>(user.getActivities()));
        parcours.setUser(user);

        parcoursRepository.saveAndFlush(parcours);

        ra.addFlashAttribute("success", "Bravo! Votre retour a été pris en compte");
        return "redirect:/user";
    }
}