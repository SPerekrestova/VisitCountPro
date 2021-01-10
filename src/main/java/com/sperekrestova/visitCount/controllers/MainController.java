package com.sperekrestova.visitCount.controllers;

import com.sperekrestova.visitCount.model.User;
import com.sperekrestova.visitCount.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Svetlana
 * Date: 02.01.2021
 */
@Controller
@AllArgsConstructor
public class MainController {

    private final GroupRepository groupRepository;

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

    @GetMapping("/groups")
    public String groups(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("lectureGroups", user.getLectureGroups());
        return "groups";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        return "login";
    }
}
