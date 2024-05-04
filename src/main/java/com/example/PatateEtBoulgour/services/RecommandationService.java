package com.example.PatateEtBoulgour.services;

import com.example.PatateEtBoulgour.dto.Coordonnees;
import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.example.PatateEtBoulgour.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RecommandationService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DistanceService distanceService;

    public HashSet<Activity> recommandFor(User user){
        List<Activity> allActivities = activityRepository.findAll();
        List<Activity> candidates = new ArrayList<>();

        // trie par distance à l'utilisateur
        try {
            Coordonnees userCoords = addressService.getCoordinates(user.getAddress());
            allActivities = distanceService.sortActivitiesByDistance(allActivities, userCoords);
        } catch (InvalidApiResponse | InvalidAddressException e) {}

        // suppression des activités connue de l'utilisateur
        for (Activity activity : allActivities) {
            if (!user.getActivities().contains(activity)) {
                candidates.add(activity);
            }
        }

        // Les prioriétés de set "vont" randomiser les choix.
        HashSet<Activity> matches = new HashSet<>();

        // Ajout des activités par match avec la pathologie.
        for (Activity activity : candidates) {
            if (activity.getPathologies().contains(user.getPathology())) {
                matches.add(activity);
            }

            if (matches.size() > 10) {
                break;
            }
        }

        return matches;
    }
}
