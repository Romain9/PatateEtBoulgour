package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiciplinesRepository extends JpaRepository<Discipline, Long> {

    @Query("select label from Discipline")
    List<String> getAllLabel();

}