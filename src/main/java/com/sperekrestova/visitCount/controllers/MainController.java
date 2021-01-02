package com.sperekrestova.visitCount.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Svetlana
 * Date: 02.01.2021
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/timetable")
    public String timetable(Model model) {
        return "timetable";
    }

    @GetMapping("/marks")
    public String marks(Model model) {
        return "marks";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }
}
