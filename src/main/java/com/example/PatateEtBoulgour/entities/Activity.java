package com.example.PatateEtBoulgour.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_activity;

    private String label;
    private String description;
    private String url;
    private Double lat;
    private Double lng;
    private String address;

    @ManyToMany
    @JoinTable(name = "activity_pathology",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "pathology_id"))
    private Set<Pathology> pathologies;

    @ManyToMany
    @JoinTable(name = "activity_discipline",
            joinColumns = @JoinColumn(name = "id_activity"),
            inverseJoinColumns = @JoinColumn(name = "id_discipline"))
    private Set<Discipline> disciplines;


}