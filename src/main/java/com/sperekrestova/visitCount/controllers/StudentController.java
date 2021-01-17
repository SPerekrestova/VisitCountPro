package com.sperekrestova.visitCount.controllers;

import com.sperekrestova.visitCount.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Svetlana
 * Date: 16.01.2021
 */
@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("/{id}")
    public String showStudent(@PathVariable("id") int id, Model model) {
        model.addAttribute("student", studentRepository.findById((long) id).get());
        return "student/student";
    }

}
