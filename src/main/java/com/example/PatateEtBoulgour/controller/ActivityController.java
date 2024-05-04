package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.ActivityRating;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.ActivityRatingRepository;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import com.example.PatateEtBoulgour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    ActivityRatingRepository activityRatingRepository;

    @RequireLogged
    @GetMapping("/add/{activityId}")
    public String addActivity(@PathVariable("activityId") Long activityId, @RequestHeader String referer) {
        User user = userService.getCurrentUser();
        Optional<Activity> activity = activityRepository.findById(activityId);
        activity.ifPresent(x -> userService.addActivity(user, x));
        return "redirect:" + referer;
    }

    @RequireLogged
    @GetMapping("/remove/{activityId}")
    public String removeActivity(@PathVariable("activityId") Long activityId, @RequestHeader String referer) {
        User user = userService.getCurrentUser();
        Optional<Activity> activity = activityRepository.findById(activityId);
        activity.ifPresent(x -> userService.removeActivity(user, x));
        return "redirect:" + referer;
    }

}
