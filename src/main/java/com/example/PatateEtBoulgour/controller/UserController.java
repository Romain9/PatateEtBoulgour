package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.dto.Coordonnees;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import com.example.PatateEtBoulgour.services.AddressService;
import com.example.PatateEtBoulgour.services.DistanceService;
import com.example.PatateEtBoulgour.services.RecommandationService;
import com.example.PatateEtBoulgour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private RecommandationService recommandationService;

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

        mv.addObject("inProfile", true);

        mv.addObject("activities", activities);

        return mv;
    }
}