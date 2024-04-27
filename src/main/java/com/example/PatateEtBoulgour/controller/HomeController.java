package com.example.PatateEtBoulgour.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {
    
    @RequestMapping
    public ModelAndView home(HttpSession session) {
        Map<String, Object> m = new HashMap<>();
        List<String> model = new ArrayList<>();

        model.add("1");
        model.add("2");
        model.add("3");
        model.add("4");
        model.add("5");

        m.put("number", model);
        if(session.getAttribute("username") != null)
            m.put("username", session.getAttribute("username"));

        return new ModelAndView("index", m);
    }

    @RequestMapping("/user")
    public ModelAndView account() {
        Map<String, Object> m = new HashMap<>();
        m.put("Name", "Romain");
        return new ModelAndView("profile", m);
    }
}
