package com.example.PatateEtBoulgour.controller;

import com.example.PatateEtBoulgour.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/user-list")
    public ModelAndView userList() {
        return userService.getUserListModelAndView();
    }

    @GetMapping("/admin/add-new-user")
    public ModelAndView addNewUser() {
        return userService.getNewUserFormModelAndView();
    }
}
