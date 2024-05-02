package com.example.PatateEtBoulgour.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PatateEtBoulgour.entities.Pathology;


import java.util.List;
import java.util.Optional;

@Repository
public interface PathologyRepository extends JpaRepository<Pathology, Long> {
    public Optional<Pathology> findById(Long id);

    public List<Pathology> findAllByOrderByLabelDesc();
}
