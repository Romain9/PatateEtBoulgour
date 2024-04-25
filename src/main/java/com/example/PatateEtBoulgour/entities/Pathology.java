package com.example.PatateEtBoulgour.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pathology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pathology;

    private String label;

    @ManyToMany(mappedBy = "pathologies")
    private Set<Activity> activities;

    @ManyToMany(mappedBy = "pathologies")
    private Set<User> users;


}


