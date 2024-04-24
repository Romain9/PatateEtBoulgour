package com.example.PatateEtBoulgour;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/admin/hello")
    public String getAdmin() {
        return "hello";
    }

    @GetMapping("/admin/ntm")
    public String getYay() {
        return "ntm";
    }

}
