package com.example.PatateEtBoulgour.controller.rest;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.ActivityRating;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.ActivityRatingRepository;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import com.example.PatateEtBoulgour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/activity/")
public class ActivityApi {

    @Autowired
    UserService userService;

    @Autowired
    ActivityRatingRepository activityRatingRepository;

    @Autowired
    ActivityRepository activityRepository;

    @RequireLogged
    @GetMapping(value = "/rating/{activityId}")
    public ResponseEntity<Integer> rate(@PathVariable Long activityId) {
        User user = userService.getCurrentUser();
        Optional<ActivityRating> ar = activityRatingRepository.findActivityRatingByActivity_IdAndUser_Id(activityId, user.getId());

        return ar.map(
                activityRating -> ResponseEntity.ok(activityRating.getRating()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(0)
        );
    }

    @RequireLogged
    @GetMapping(value = "/rating/{activityId}/{value}")
    public ResponseEntity<Map> noteActivity(@PathVariable("activityId") Long activityId, @PathVariable("value") int value) {
        User user = userService.getCurrentUser();
        Optional<Activity> activity = activityRepository.findById(activityId);

        if (activity.isPresent()) {
            Optional<ActivityRating> note = activityRatingRepository.findActivityRatingByActivity_IdAndUser_Id(activityId, user.getId());
            if (note.isPresent()) {
                ActivityRating activityRating = note.get();
                activityRating.setRating(value);
                activityRatingRepository.save(activityRating);
            } else {
                activityRatingRepository.save(
                        ActivityRating.builder().activity(activity.get()).user(user).rating(value).build()
                );
            }

            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "success"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "error"));
    }

    @RequireLogged
    @GetMapping(value = "/add/{activityId}")
    public ResponseEntity<Map> addActivity(@PathVariable("activityId") Long activityId) {
        User user = userService.getCurrentUser();
        Optional<Activity> activity = activityRepository.findById(activityId);
        activity.ifPresent(x -> userService.addActivity(user, x));
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "success"));
    }

    @RequireLogged
    @GetMapping(value = "/remove/{activityId}")
    public ResponseEntity<Map> removeActivity(@PathVariable("activityId") Long activityId) {
        User user = userService.getCurrentUser();
        Optional<Activity> activity = activityRepository.findById(activityId);
        activity.ifPresent(x -> userService.removeActivity(user, x));
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "success"));
    }

}
