package com.example.PatateEtBoulgour.config.profiles;

import com.example.PatateEtBoulgour.entities.Pathology;
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
                    .age(21)
                    .gender("Male")
                    .address("60 Rue du Plat d'Étain, 37000 Tours")
                    .firstName("Admin")
                    .lastName("Principal")
                    .password("admin501")
                    .username("Admin")
                    .email("admin@root.fr")
                    .role(Role.SUPER_ADMIN)
                    .build();

            User utilisateur = User.builder()
                    .age(69)
                    .gender("Male")
                    .address("2 Bd Tonnellé, 37000 Tours")
                    .firstName("Michel")
                    .lastName("Platin")
                    .password("mot de passe")
                    .username("michel2")
                    .email("michelle2@gmail.com")
                    .role(Role.USER)
                    .pathology(
                            new Pathology(5L, "")
                    )
                    .build();

            User utilisateur2 = User.builder()
                    .age(89)
                    .gender("Female")
                    .address("2 Bd Tonnellé, 37000 Tours")
                    .firstName("Jaqueline")
                    .lastName("Roseau")
                    .password("123456789")
                    .username("jaqueline.roseau")
                    .email("jaqueline5@ymail.com")
                    .role(Role.USER)
                    .pathology(
                            new Pathology(2L, "")
                    )
                    .build();
            userRepository.save(admin);
            userRepository.save(utilisateur);
            userRepository.save(utilisateur2);
        };
    }
}