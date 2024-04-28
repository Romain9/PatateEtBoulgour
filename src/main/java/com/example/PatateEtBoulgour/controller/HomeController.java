package com.example.PatateEtBoulgour.controller;

import java.util.*;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.Pathology;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.ActivityService;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping
    public ModelAndView home() {
        Map<String, Object> m = new HashMap<>();

        User user = userService.getCurrentUser();
        if (user != null) {
            m.put("user", user); // Ajoutez l'utilisateur à la map
        }

        Set<Activity> activities = activityService.getAllActivities();
        m.put("activities", activities);

        return new ModelAndView("index", m);
    }


    @RequestMapping("/user")
    public ModelAndView account() {
        Map<String, Object> m = new HashMap<>();


        User romain = User
                .builder()
                .firstName("Romain")
                .lastName("Msiah")
                .age(77)
                .address("50 rue des pasteque")
                .gender("Arbre")
                .pathologies(List.of(Pathology.builder().label("Pastafari").build(), Pathology.builder().label("CSS Lover").build()))
                .build();

        m.put("Name", "Romain");
        m.put("user", romain);

        return new ModelAndView("profile", m);
    }
}
