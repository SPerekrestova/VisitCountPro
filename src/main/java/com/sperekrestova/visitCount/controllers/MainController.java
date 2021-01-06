package com.sperekrestova.visitCount.controllers;

import com.sperekrestova.visitCount.model.User;
import com.sperekrestova.visitCount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Svetlana
 * Date: 02.01.2021
 */
@Controller
public class MainController {

    @Autowired
    private UserRepository userRepo;

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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "register_success";
    }
}
