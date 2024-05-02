package com.example.PatateEtBoulgour.repository;

import com.example.PatateEtBoulgour.entities.Pathology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {
    @Query("select label from Pathology")
    List<String> getAllLabel();

}