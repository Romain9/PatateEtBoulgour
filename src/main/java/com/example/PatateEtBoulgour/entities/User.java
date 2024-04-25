package com.example.PatateEtBoulgour.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    private String nom;
    private String prenom;
    private String email;
    private int age;
    private String genre;
    private Double lat;
    private Double lng;
    private String address;

    @ManyToMany
    @JoinTable(name = "user_pathology",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pathology_id"))
    private Set<Pathology> pathologies;

    @OneToMany(mappedBy = "user")
    private List<Route> routes;

}

