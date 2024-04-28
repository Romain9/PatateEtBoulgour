package com.example.PatateEtBoulgour.config.profiles;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"preprod", "devNoChange"})
public class PreProdConfig {

}