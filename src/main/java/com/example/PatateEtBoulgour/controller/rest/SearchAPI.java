package com.example.PatateEtBoulgour.controller.rest;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import com.example.PatateEtBoulgour.repository.DiciplinesRepository;
import com.example.PatateEtBoulgour.repository.PathologyRepository;
import com.example.PatateEtBoulgour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchAPI {

    @Autowired
    private PathologyRepository pathologyRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private DiciplinesRepository diciplinesRepository;

    @GetMapping("/search/pathologies")
    public List<String> getPathologies() {
        return pathologyRepository.getAllLabel();
    }

    @GetMapping("/search/titre")
    public List<String> getActivities() {
        return activityRepository.getAllLabel();
    }

    @GetMapping("/search/disciplines")
    public List<String> getDiciplines() {
        return diciplinesRepository.getAllLabel();
    }



}
