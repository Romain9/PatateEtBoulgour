package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.annotations.RequireRole;
import com.example.PatateEtBoulgour.dto.RatedActivity;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.ActivityRating;
import com.example.PatateEtBoulgour.entities.Parcours;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.ActivityRatingRepository;
import com.example.PatateEtBoulgour.repository.ParcoursRepository;
import com.example.PatateEtBoulgour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ParcoursRepository parcoursRepository;

    @Autowired
    private ActivityRatingRepository activityRatingRepository;

    @RequireLogged
    @RequireRole("Admin")
    @GetMapping("/utilisateurs-list")
    public ModelAndView userList(ModelAndView modelAndView) {
        List<User> users = userRepository.findAll();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/userlist");

        return modelAndView;
    }

    @RequireLogged
    @RequireRole("Admin")
    @GetMapping("/consulter-user/{id}")
    public String userDetail(Model model, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return "redirect:/admin/user-list";
        }

        model.addAttribute("user", user.get());
        return "admin/userdetail";
    }

    @RequireLogged
    @RequireRole("Admin")
    @GetMapping("/consulter-parcours/")
    public String parcoursList(Model model) {
        List<Parcours> parcours = parcoursRepository.findAll();

        model.addAttribute("parcours", parcours);
        return "admin/parcourslist";
    }

    @RequireLogged
    @RequireRole("Admin")
    @GetMapping("/consulter-activités/")
    public String activityList(Model model) {
        HashMap<Activity, ArrayList<ActivityRating>> mappedActivites = new HashMap<>();

        for (ActivityRating ar : activityRatingRepository.findAll()) {
                Activity ac = ar.getActivity();
                if (mappedActivites.containsKey(ac)) {
                    mappedActivites.get(ac).add(ar);
                } else {
                    ArrayList<ActivityRating> activities = new ArrayList<>();
                    activities.add(ar);
                    mappedActivites.put(ac, activities);
                }
        }

        List<RatedActivity> ratings = new ArrayList<>();
        for (Activity ac : mappedActivites.keySet()) {
            double avg = mappedActivites.get(ac)
                    .stream()
                    .mapToDouble(ActivityRating::getRating).average()
                    .orElse(0.0);

            ratings.add(new RatedActivity(ac.getId(), ac.getLabel(), ac.getAddress(), avg));
        }

        model.addAttribute("rating", ratings);
        return "admin/activitylist";
    }


}
