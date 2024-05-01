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
    public ModelAndView home() {
        Map<String, Object> m = new HashMap<>();
        

        User user = userService.getCurrentUser();
        if (user != null)  m.put("user", user);
                

        Pageable page = PageRequest.of(0, 10);
        List<Activity> activities = activityService.getAllActivities(page);

         
        if (!activityService.getAllActivities(page.next()).isEmpty()) {
            m.put("nextPage", 1);
        }
        
        
        m.put("activities", activities);

        return new ModelAndView("index", m);
    }

    @PostMapping()
    public String homeSearch(@RequestParam("search") String activityKeywords, Model m) {
        Set<Activity> activities = activityService.getActivityContainingKeyword(activityKeywords);
        m.addAttribute("activities", activities);

        
        m.addAttribute("user", userService.getCurrentUser());

        return "components/activities";
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

        if (page.hasPrevious()) {
            m.put("previousPage", page.previousOrFirst().getPageNumber());
        }

        List<Activity> activities = activityService.getAllActivities(page);
        m.put("activities", activities);

        if (!activityService.getAllActivities(page.next()).isEmpty()) {
            m.put("nextPage", page.next().getPageNumber());
        }


        return new ModelAndView("index", m);
    }
}
