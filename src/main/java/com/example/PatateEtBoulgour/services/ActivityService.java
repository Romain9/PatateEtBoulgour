package com.example.PatateEtBoulgour.services;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<Activity> getActivityContainingKeyword(String keyword) {
        Iterable<Activity> activities = activityRepository.findByContainsLabelOrDescription(keyword);
        HashSet<Activity> activitiesSet = new HashSet<>();
        activities.forEach(activitiesSet::add);
        return activitiesSet;
    }

    public List<Activity> getActivityContainingKeyword(String opt, String keyword, Pageable pageable) {
        List<Activity> act;

        switch (opt) {
            case "titre":
                act = activityRepository.findByContainsLabelOrDescription(keyword, pageable);
                break;
            case "description":
                act = activityRepository.findByContainsLabelOrDescription(keyword, pageable);
                break;
            case "pathologies":
                act = activityRepository.findByContainsLabelOrDescription(keyword, pageable);
                break;
            case "disciplines":
                act = activityRepository.findByContainsLabelOrDescription(keyword, pageable);
                break;
            default:
                act = activityRepository.findByContainsLabelOrDescription(keyword, pageable);
                break;
        }
        return act;
    }

    public void addUserToActivity(User user, Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if(activity == null) throw new EmptyResultDataAccessException("Activity not found", 1);
        activityRepository.addUser(user, activity);
        activityRepository.saveAndFlush(activity);
    }
}
