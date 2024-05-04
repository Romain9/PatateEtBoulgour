package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.Activity;
import com.example.PatateEtBoulgour.entities.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a JOIN a.pathologies p WHERE p.label LIKE CONCAT('%', :keyword, '%')")
    List<Activity> findActivitiesByPathologyLabel(@Param("keyword") String pathologyLabel);
    
    /* Pour la pagination */

    @Query("SELECT a FROM Activity a WHERE a.label LIKE CONCAT('%', :keyword, '%') OR a.description LIKE CONCAT('%', :keyword, '%')")
    List<Activity> findByContainsLabelOrDescription(@Param("keyword") String keyword, Pageable page);

    @Query("SELECT a FROM Activity a JOIN a.pathologies p WHERE p.label LIKE CONCAT('%', :keyword, '%')")
    List<Activity> findActivitiesByPathologyLabel(@Param("keyword") String pathologyLabel, Pageable page);

    @Query("SELECT a FROM Activity a JOIN a.disciplines d WHERE d.label LIKE CONCAT('%', :keyword, '%')")
    List<Activity> findActivitiesByDisciplineLabel(@Param("keyword") String disciplineLabel, Pageable page);

    @Query("select label from Activity")
    List<String> getAllLabel();

}
