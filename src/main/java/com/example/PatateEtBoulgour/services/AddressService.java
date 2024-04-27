package com.example.PatateEtBoulgour.services;


import com.example.PatateEtBoulgour.exception.InvalidAddressException;
import com.example.PatateEtBoulgour.exception.InvalidApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.PatateEtBoulgour.dto.Coordonnees;

import java.util.Optional;

@Service
public class AddressService {

    public Coordonnees getCoordinates(String address) throws InvalidApiResponse, InvalidAddressException {
        String url = "https://api-adresse.data.gouv.fr/search/?q=" + address.replaceAll("\\s+", "+");

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(url, String.class);

        return parseCoordinatesFromResponse(response);
    }

    private Coordonnees parseCoordinatesFromResponse(String response) throws InvalidApiResponse, InvalidAddressException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            JsonNode features = jsonResponse.get("features");

            if (features != null && features.isArray() && !features.isEmpty()) {
                JsonNode firstFeature = features.get(0);
                JsonNode geometry = firstFeature.get("geometry");
                JsonNode coordinates = geometry.get("coordinates");
                JsonNode properties = firstFeature.get("properties");
                Double score = properties.get("score").asDouble();

                if (score > 0.75) {
                    double longitude = coordinates.get(0).asDouble();
                    double latitude = coordinates.get(1).asDouble();

                    return new Coordonnees(latitude, longitude);
                }

                throw new InvalidAddressException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        throw new InvalidApiResponse();
    }
}
