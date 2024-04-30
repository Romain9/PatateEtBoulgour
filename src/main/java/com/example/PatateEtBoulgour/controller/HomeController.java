package com.example.PatateEtBoulgour.controller;

import java.util.*;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.annotations.RequireRole;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.ActivityService;
import com.example.PatateEtBoulgour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

        Pageable page = PageRequest.of(0, 10);

        if (page.next().isPaged()) {
            m.put("nextPage", 1);
        }

        List<Activity> activities = activityService.getAllActivities(page);
        m.put("activities", activities);

        return new ModelAndView("index", m);
    }

    @RequestMapping("/{id}")
    public ModelAndView getActivityForPage(@PathVariable int id) {
        Map<String, Object> m = new HashMap<>();

        if (id < 0) {
            m.put("errorMessage", "Eh oh, tu arrêtes!");
            return new ModelAndView("redirect:/");
        }

        User user = userService.getCurrentUser();
        if (user != null)  m.put("user", user);

        Pageable page = PageRequest.of(id, 10);

        if (page.next().isPaged()) {
            m.put("nextPage", page.next().getPageNumber());
        }

        if (page.hasPrevious()) {
            m.put("previousPage", page.previousOrFirst().getPageNumber());
        }

        List<Activity> activities = activityService.getAllActivities(page);
        m.put("activities", activities);

        return new ModelAndView("index", m);
    }
}
