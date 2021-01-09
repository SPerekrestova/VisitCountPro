package com.sperekrestova.visitCount.controllers;

import com.sperekrestova.visitCount.model.Role;
import com.sperekrestova.visitCount.model.User;
import com.sperekrestova.visitCount.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

/**
 * Created by Svetlana
 * Date: 09.01.2021
 */
@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UserRepository userRepo;

    @GetMapping()
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping()
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userRepo.save(user);
        return "register_success";
    }
}
