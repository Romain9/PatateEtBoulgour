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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String home(Model m) {
        m = search(m, "", 0);
        m.addAttribute("unlogged", !userService.isLoggedIn());

        return "index";
    }

    private Model search(Model m, String activityKeywords, int numPage) {

        Pageable page = PageRequest.of(0, 10*(numPage+1));

        List<Activity> activities = activityService.getActivityContainingKeyword(activityKeywords, page);

        m.addAttribute("activities", activities);

        m.addAttribute("user", userService.getCurrentUser());

        m.addAttribute("nbPage", numPage+1);

        return m;
    }

    @PostMapping()
    public String homeSearch(@RequestParam("search") String activityKeywords, @RequestParam("page") int numPage, Model m) {
        m = search(m, activityKeywords, numPage);
        m.addAttribute("unlogged", !userService.isLoggedIn());

        return "components/activities";
    }
}
