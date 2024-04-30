package com.example.PatateEtBoulgour.services;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    public List<Activity> getAllActivities(){
        return activityRepository.findAllByOrderByLabelDesc();
    }

    public void addUserToActivity(User user, Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if(activity == null) throw new EmptyResultDataAccessException("Activity not found", 1);
        activityRepository.addUser(user, activity);
    }
}
