package com.example.PatateEtBoulgour.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parcours {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "La note doit être au moins de 0")
    @Max(value = 10, message = "La note doit être au maximum de 10")
    private int rating;

    @Length(min = 5, message = "Veuillez laisser un commentaire")
    private String commentaire;

    @ManyToOne
    private User user;

    @CurrentTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @ManyToMany(cascade = CascadeType.ALL)
    List<Activity> activities;
}
