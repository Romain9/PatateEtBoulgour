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
public class Discipline {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String label;

    @ManyToOne
    Activity activity;

}


