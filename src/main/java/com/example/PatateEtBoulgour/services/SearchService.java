package com.example.PatateEtBoulgour.services;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.PathologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class SearchService {

    private final int pageSize = 5;

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    public Model search(Model m, String opt, String activityKeywords, int numPage) {
        Pageable page = PageRequest.of(0, pageSize*(numPage+1));
        List<Activity> activities = activityService.getActivityContainingKeyword(opt, activityKeywords, page);

        // Si l'utilisateur est connecté
        // il faut déterminer si l'activité est à ajouter ou supprimer
        // lors de la première génération de la page.
        if (userService.isLoggedIn()) {
            User user = userService.getCurrentUser();
            m.addAttribute("user", user);

            // pour chaque activité de l'utilisateur
            // marquer les activités comme appartenant à l'utilisateur
            for (Activity activity : activities) {
                if (user.getActivities().contains(activity)) {
                    activity.setContainsCurrentUser(true);
                }
            }
        } else {
            m.addAttribute("unlogged", !userService.isLoggedIn());
        }

        m.addAttribute("activities", activities);
        m.addAttribute("nbPage", numPage+1);

        return m;
    }
}
