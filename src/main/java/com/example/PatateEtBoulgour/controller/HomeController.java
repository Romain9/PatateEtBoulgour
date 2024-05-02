package com.example.PatateEtBoulgour.controller;

import java.util.*;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.ActivityService;
import com.example.PatateEtBoulgour.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        m = search(m, "", "", 0);

        return "index";
    }

    private Model search(Model m, String opt, String activityKeywords, int numPage) {

        Pageable page = PageRequest.of(0, 10*(numPage+1));

        List<Activity> activities = activityService.getActivityContainingKeyword(opt, activityKeywords, page);

        m.addAttribute("activities", activities);

        if (userService.isLoggedIn()) {
            User user = userService.getCurrentUser();
            m.addAttribute("user", user);

            for (Activity activity : activities) {
                if (user.getActivities().contains(activity)) {
                    activity.setContainsCurrentUser(true);
                }
            }

        } else {
            m.addAttribute("unlogged", !userService.isLoggedIn());
        }

        m.addAttribute("nbPage", numPage+1);

        return m;
    }


    @PostMapping()
    public String homeSearch(@RequestParam(value = "option", defaultValue = "") String opt, @RequestParam("search") String activityKeywords, @RequestParam("page") Optional<Integer> numPage, Model m) {

        if (!numPage.isPresent()) {
            return "redirect:/";
        }

        m = search(m, opt, activityKeywords, numPage.get());

        return "components/activities";
    }
}
