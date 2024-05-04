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

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    public Model search(Model m, String opt, String activityKeywords, int numPage) {

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
}
