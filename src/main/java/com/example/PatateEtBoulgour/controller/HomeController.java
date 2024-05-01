package com.example.PatateEtBoulgour.controller;

import java.util.*;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.annotations.RequireRole;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.ActivityService;
import com.example.PatateEtBoulgour.services.UserService;

import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @GetMapping()
    public ModelAndView home() {
        Map<String, Object> m = new HashMap<>();

        User user = userService.getCurrentUser();
        if (user != null)  m.put("user", user);

        Set<Activity> activities = activityService.getAllActivities();
        m.put("activities", activities);

        return new ModelAndView("index", m);
    }

    @PostMapping("/search")
    public String homeSearch(@RequestParam("search") String activityKeywords, Model m) {
        Set<Activity> activities = activityService.getActivityContainingKeyword(activityKeywords);
        m.addAttribute("activities", activities);

        return "activities :: activities";
    }
}
