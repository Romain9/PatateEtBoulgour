package com.example.PatateEtBoulgour.services;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    public Set<Activity> getAllActivities(){
        Iterable<Activity> activities = activityRepository.findAllByOrderByLabelDesc();
        HashSet<Activity> activitiesSet = new HashSet<>();
        activities.forEach(activitiesSet::add);
        return activitiesSet;
    }

    public Set<Activity> getActivityContainingKeyword(String keyword) {
        Iterable<Activity> activities = activityRepository.findByContainsLabelOrDescription(keyword);
        HashSet<Activity> activitiesSet = new HashSet<>();
        activities.forEach(activitiesSet::add);
        return activitiesSet;
    }

    public void addUserToActivity(User user, Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if(activity == null) throw new EmptyResultDataAccessException("Activity not found", 1);
        activityRepository.addUser(user, activity);
    }
}
