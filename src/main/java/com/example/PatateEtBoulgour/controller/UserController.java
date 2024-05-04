package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.Pathology;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import com.example.PatateEtBoulgour.repository.PathologyRepository;
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
    private PathologyRepository pathologyRepository;


    @RequireLogged
    @RequestMapping("/user")
    public ModelAndView account() {
        ModelAndView mv = new ModelAndView("profile");

        User user = userService.getCurrentUser();
        if (user != null){

            Set<Activity> activities = userService.getUserActivities(user);

            mv.addObject("user", user);

            for (Activity activity : activities) {
                activity.setContainsCurrentUser(true);
            }

            Pathology pathoUser = user.getPathology();
            if (pathoUser != null) 
                mv.addObject("activitiesCar",
                        activityRepository.findActivitiesByPathologyLabel(pathoUser.getLabel())
                );

            mv.addObject("inProfile", true);

            mv.addObject("activities", activities);
        }

        return mv;
    }

}
