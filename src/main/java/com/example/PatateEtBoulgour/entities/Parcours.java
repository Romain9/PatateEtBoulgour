package com.example.PatateEtBoulgour.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parcours {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private double note;

    @OneToOne
    private User user;

    @OneToMany
    List<Activity> activities;
}
