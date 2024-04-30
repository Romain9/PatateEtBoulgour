package com.example.PatateEtBoulgour.entities;

import com.example.PatateEtBoulgour.enums.Role;
import com.example.PatateEtBoulgour.services.PasswordService;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

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
    private Long id;

    @Column(name="password")
    private String passwordHash;

    @NotBlank(message = "Le nom d'utilisateur est requis")
    @Size(min = 4, max = 20, message = "Le nom d'utilisateur doit comporter entre 4 et 20 caractères")
    @Unique
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 6, message = "Le mot de passe doit comporter au moins 6 caractères")
    @Transient
    private String password;

    @NotBlank(message = "Le nom de famille est requis")
    private String lastName;

    @NotBlank(message = "Le prénom est requis")
    private String firstName;

    @Email(message = "Format email invalide")
    @Unique
    private String email;

    @Min(value = 18, message = "L'âge doit être d'au moins 18 ans")
    @Max(value = 150, message = "L'âge doit être inférieur à 150 ans")
    private int age;

    @NotBlank(message = "Le genre est requis")
    private String gender;

    @NotBlank(message = "L'adresse est requise")
    @Pattern(regexp = "^(\\d+)\\s+(.+)\\s*,\\s*(\\d{5})\\s+(.+)$", message = "Format d'adresse invalide: Numéro Nom Rue, Code Postal Ville")
    private String address; // Format: Numéro de rue Nom de rue, Code postal Ville

    @OneToMany
    private List<Pathology> pathologies;

    @ManyToMany(mappedBy = "participants")
    private Set<Activity> activities;

    public void setPassword(String password) {
        this.password = password;
        this.passwordHash = PasswordService.encodePassword(password);
    }

    public String getPassword() {
        return this.passwordHash;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static class UserBuilder {
        public UserBuilder password(String password) {
            this.password = password;
            this.passwordHash = PasswordService.encodePassword(password);
            return this;
        }

        public UserBuilder passwordHash(String passwordHash) {
            throw new UnsupportedOperationException("Veuillez utiliser le .password");
        }
    }

    public User(String username, String password, String lastName, String firstName, String email, int age, String gender, String address) {
        this.username = username;
        setPassword(password);
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }
}

