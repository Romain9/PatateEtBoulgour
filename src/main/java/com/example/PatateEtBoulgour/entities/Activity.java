package com.example.PatateEtBoulgour.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String label;
    private String description;
    private String url;
    private Double lat;
    private Double lng;
    private String address;

    @ManyToMany
    private List<Pathology> pathologies;

    @ManyToMany
    private List<Discipline> disciplines;


}