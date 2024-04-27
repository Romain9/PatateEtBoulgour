package com.example.PatateEtBoulgour.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.PatateEtBoulgour.entities.Coordonnees;

import java.util.Optional;

@Service
public class AddressService {

    public Optional<Coordonnees> getCoordinates(String address) {
        String url = "https://api-adresse.data.gouv.fr/search/?q=" + address.replaceAll("\\s+", "+");

        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(url, String.class);

            return parseCoordinatesFromResponse(response);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<Coordonnees> parseCoordinatesFromResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonResponse = objectMapper.readTree(response);

            JsonNode features = jsonResponse.get("features");

            if (features != null && features.isArray() && features.size() > 0) {
                JsonNode firstFeature = features.get(0);
                JsonNode geometry = firstFeature.get("geometry");
                JsonNode coordinates = geometry.get("coordinates");
                JsonNode properties = firstFeature.get("properties");
                Double score = properties.get("score").asDouble();

                if (score > 0.75) {
                    double longitude = coordinates.get(0).asDouble();
                    double latitude = coordinates.get(1).asDouble();

                    return Optional.of(new Coordonnees(latitude, longitude));
                }
            }
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.empty();
    }
}
