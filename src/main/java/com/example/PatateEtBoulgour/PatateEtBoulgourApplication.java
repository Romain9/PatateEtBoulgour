package com.example.PatateEtBoulgour;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;


@SpringBootApplication
public class PatateEtBoulgourApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatateEtBoulgourApplication.class, args);
	}

}
