package com.example.PatateEtBoulgour.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    private Double latitude;
    private Double longitude;
    private String address;

    @Transient
    private boolean containsCurrentUser = false;

    @ManyToMany
    private List<Pathology> pathologies;

    @ManyToMany
    private List<Discipline> disciplines;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    Set<User> participants;

    public boolean userIsParticipant(User user) {
        return participants.contains(user);
    }

    public boolean equals(Activity o) {
        return Objects.equals(o.getId(), this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLabel(), getDescription(), getUrl(), getLatitude(), getLongitude(), getAddress());
    }
}