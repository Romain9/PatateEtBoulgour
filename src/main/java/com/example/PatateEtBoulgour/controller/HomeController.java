package com.example.PatateEtBoulgour.controller;

import java.util.*;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.annotations.RequireRole;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.ActivityService;
import com.example.PatateEtBoulgour.services.UserService;
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

    @RequestMapping()
    public ModelAndView home() {
        Map<String, Object> m = new HashMap<>();

        User user = userService.getCurrentUser();
        if (user != null)  m.put("user", user);

        Set<Activity> activities = activityService.getAllActivities();
        m.put("activities", activities);

        return new ModelAndView("index", m);
    }

    @RequireLogged
    @RequestMapping("/user")
    public ModelAndView account() {
        Map<String, Object> m = new HashMap<>();

        ModelAndView mv = new ModelAndView("profile");

        User user = userService.getCurrentUser();
        if (user != null){
            m.put("user", user);

            Set<Activity> activities = activityService.getAllActivities();
            mv.addObject("user", user);
            mv.addObject("activities", activities);
            //mv.addObject("pathologies", pathologies);
            m.put("activities", activities);
        }



        return new ModelAndView("profile", m);
    }
}
