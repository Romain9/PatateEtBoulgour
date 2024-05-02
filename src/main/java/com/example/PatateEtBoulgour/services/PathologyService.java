package com.example.PatateEtBoulgour.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PatateEtBoulgour.entities.Pathology;
import com.example.PatateEtBoulgour.repository.PathologyRepository;

@Service
public class PathologyService {
    @Autowired
    PathologyRepository pathologyRepository;

}
