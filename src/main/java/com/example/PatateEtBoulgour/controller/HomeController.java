package com.example.PatateEtBoulgour.controller;

import java.util.*;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.ActivityService;
import com.example.PatateEtBoulgour.services.SearchService;
import com.example.PatateEtBoulgour.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
    private SearchService searchService;

    @GetMapping()
    public String home(Model m) {
        searchService.search(m, "", "", 0);

        return "index";
    }

    @PostMapping()
    public String homeSearch(@RequestParam(value = "option", defaultValue = "") String opt, @RequestParam("search") String activityKeywords, @RequestParam("page") Optional<Integer> numPage, Model m) {

        if (numPage.isEmpty()) {
            return "redirect:/";
        }

        searchService.search(m, opt, activityKeywords, numPage.get());

        return "components/activities";
    }
}
