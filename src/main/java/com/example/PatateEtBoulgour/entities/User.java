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
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String lastName;

    private String firstName;

    private String email;

    private int age;

    private String gender;

    private Double lat;

    private Double lng;

    private String address;

    @OneToMany
    private List<Pathology> pathologies;

    @OneToMany
    private List<Activity> activities;

}

