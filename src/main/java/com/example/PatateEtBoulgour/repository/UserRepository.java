package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(long id);
    List<User> findByLastName(String lastName);
    List<User> findByAgeGreaterThanEqual(int age);
    List<User> findByGender(String gender);
    List<User> findByAddress(String address);
    List<User> findByPathologies_Label(String pathologyName);
    List<User> findByActivities_Label(String activityName);
    @Query("SELECT a FROM User u JOIN u.activities a WHERE u.id = :userId")
    Set<Activity> findActivitiesByUserId(Long userId);



}
