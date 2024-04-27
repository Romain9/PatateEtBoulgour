package com.example.PatateEtBoulgour.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordonnees {
    private double latitude;
    private double longitude;

}