package com.example.PatateEtBoulgour.services;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserService userService;

    public List<Activity> getActivityContainingKeyword(String keyword, Pageable pageable) {
        return activityRepository.findByContainsLabelOrDescription(keyword, pageable);
    }
}
