package com.example.PatateEtBoulgour.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile({"dev", "devNoChange"})
public class DebugErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String exception = (String) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("errorCode", status);
        model.addAttribute("errorDescription", message);
        model.addAttribute("errorException", exception);
        return "errorDebug";
    }

}
