package com.example.PatateEtBoulgour.services;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    public Set<Activity> getAllActivities(){
        Iterable<Activity> activities = activityRepository.findAll();
        HashSet<Activity> activitiesSet = new HashSet<>();
        activities.forEach(activitiesSet::add);
        return activitiesSet;
    }
}
