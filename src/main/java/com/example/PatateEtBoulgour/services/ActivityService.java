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

    public List<Activity> getAllActivities(){
        return activityRepository.findAllByOrderByLabelDesc();
    }

    public List<Activity> getAllActivities(Pageable pageable){
        return activityRepository.findAllByOrderByLabelDesc(pageable);
    }

    public List<Activity> getActivityContainingKeyword(String opt, String keyword, Pageable pageable) {
        List<Activity> act;

        switch (opt) {
            case "titre":
                act = activityRepository.findByContainsLabel(keyword, pageable);
                break;
            case "description":
                act = activityRepository.findByContainsDescription(keyword, pageable);
                break;
            case "pathologies":
                act = activityRepository.findActivitiesByPathologyLabel(keyword, pageable);
                break;
            case "disciplines":
                act = activityRepository.findActivitiesByDisciplineLabel(keyword, pageable);
                break;
            default:
                act = activityRepository.findByContainsLabelOrDescription(keyword, pageable);
                break;
        }
        return act;
    }
    
    public List<Activity> getActivityContainingKeyword(String keyword, Pageable pageable) {
        return activityRepository.findByContainsLabelOrDescription(keyword, pageable);
    }
}
