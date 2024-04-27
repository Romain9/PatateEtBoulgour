package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.entities.User;
import com.example.PatateEtBoulgour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("email") String email,
                             @RequestParam("age") int age,
                             @RequestParam("gender") String gender,
                             @RequestParam("latitude") double latitude,
                             @RequestParam("longitude") double longitude,
                             @RequestParam("address") String address) {
        // Créer l'objet User avec les données du formulaire
        User user = new User(username, password, lastName, firstName, email, age, gender, latitude, longitude, address);
        // Ajouter les coordonnées GPS et l'adresse à l'utilisateur
        userService.createUser(user);
        return "redirect:/admin/user-list";
    }

}
