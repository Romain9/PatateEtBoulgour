package com.example.PatateEtBoulgour.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatedActivity {
    private Long id;
    private String label;
    private String address;
    private double rating;
    private int nbNote;
}