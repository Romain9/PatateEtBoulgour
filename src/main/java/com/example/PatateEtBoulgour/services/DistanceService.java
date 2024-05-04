package com.example.PatateEtBoulgour.services;


import com.example.PatateEtBoulgour.dto.Coordonnees;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DistanceService {

    public double calculateDistance(Coordonnees a, Coordonnees b) {
        double theta = a.getLongitude() - b.getLongitude();
        double dist = Math.sin(Math.toRadians(a.getLatitude())) * Math.sin(Math.toRadians(b.getLatitude()))
                + Math.cos(Math.toRadians(a.getLatitude())) * Math.cos(Math.toRadians(b.getLatitude())) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist;
    }

    public List<Activity> sortActivitiesByDistance(List<Activity> activities, Coordonnees userCoords) {
        activities.sort((a1, a2) -> {
            double distance1 = calculateDistance(userCoords, new Coordonnees(a1.getLatitude(), a1.getLongitude()));
            double distance2 = calculateDistance(userCoords, new Coordonnees(a2.getLatitude(), a2.getLongitude()));
            return Double.compare(distance1, distance2);
        });
        return activities;
    }

    
}
