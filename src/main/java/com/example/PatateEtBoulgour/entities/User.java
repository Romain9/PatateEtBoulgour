package com.example.PatateEtBoulgour.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passwordHash;

    @NotBlank(message = "Le nom d'utilisateur est requis")
    @Size(min = 4, max = 20, message = "Le nom d'utilisateur doit comporter entre 4 et 20 caractères")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 6, message = "Le mot de passe doit comporter au moins 6 caractères")
    private String password;

    @NotBlank(message = "Le nom de famille est requis")
    private String lastName;

    @NotBlank(message = "Le prénom est requis")
    private String firstName;

    @Email(message = "Format email invalide")
    private String email;

    @Min(value = 18, message = "L'âge doit être d'au moins 18 ans")
    @Max(value = 150, message = "L'âge doit être inférieur à 150 ans")
    private int age;

    @NotBlank(message = "Le genre est requis")
    private String gender;

    @NotNull(message = "La latitude est requise")
    @DecimalMin(value = "-90.0", message = "La latitude doit être comprise entre -90 et 90")
    @DecimalMax(value = "90.0", message = "La latitude doit être comprise entre -90 et 90")
    private Double lat;

    @NotNull(message = "La longitude est requise")
    @DecimalMin(value = "-180.0", message = "La longitude doit être comprise entre -180 et 180")
    @DecimalMax(value = "180.0", message = "La longitude doit être comprise entre -180 et 180")
    private Double lng;

    @NotBlank(message = "L'adresse est requise")
    @Pattern(regexp = "^(\\d+)\\s+(.+)\\s*,\\s*(\\d{5})\\s+(.+)$", message = "Format d'adresse invalide")
    private String address; // Format: Numéro de rue Nom de rue, Code postal Ville

    @OneToMany
    private List<Pathology> pathologies;

    @ManyToMany
    private List<Activity> activities;

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.passwordHash = passwordEncoder.encode(password);
    }

}

