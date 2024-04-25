package com.example.PatateEtBoulgour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@SpringBootApplication
public class PatateEtBoulgourApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatateEtBoulgourApplication.class, args);
	}

}
