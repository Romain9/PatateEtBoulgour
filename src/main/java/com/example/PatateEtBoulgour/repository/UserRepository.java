package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByLastName(String lastName);
    List<User> findByAgeGreaterThanEqual(int age);
    List<User> findByGender(String gender);
    List<User> findByAddress(String address);
    List<User> findByLatitudeBetweenAndLongitudeBetween(Double minLat, Double maxLat, Double minLong, Double maxLong);
    List<User> findByPathologies_Label(String pathologyName);
    List<User> findByActivities_Label(String activityName);
}
