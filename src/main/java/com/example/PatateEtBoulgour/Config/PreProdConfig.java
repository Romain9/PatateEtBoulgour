package com.example.PatateEtBoulgour.Config;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.UserRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("preprod")
public class PreProdConfig {

}