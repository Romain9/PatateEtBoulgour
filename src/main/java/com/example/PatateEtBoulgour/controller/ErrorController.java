package com.example.PatateEtBoulgour.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String handleErrorProd(HttpServletRequest request, Model model) {
        int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String msg = "";
        if (status == 403) {
            msg = "Accès interdit!";
        } else {
            msg = "Une erreur s'est produite";
        }

        model.addAttribute("errorMessage", msg);
        return "error";
    }
}
