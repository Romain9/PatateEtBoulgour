package com.example.PatateEtBoulgour.config.profiles;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.enums.Role;
import com.example.PatateEtBoulgour.repository.UserRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev"})
public class DevConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    public SmartInitializingSingleton initDefaultData() {
        return () -> {
            User admin = User.builder()
                    .age(20)
                    .gender("Triple PHP")
                    .address("25 Je ne contracte pas, 00000 Nul Part")
                    .firstName("Jean")
                    .lastName("Sebastonks")
                    .password("tamere")
                    .username("Compliste800")
                    .role(Role.SUPER_ADMIN)
                    .build();
            userRepository.save(admin);
        };
    }
}