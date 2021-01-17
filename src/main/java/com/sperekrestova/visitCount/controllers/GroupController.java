package com.sperekrestova.visitCount.controllers;

import com.sperekrestova.visitCount.model.User;
import com.sperekrestova.visitCount.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupRepository groupRepository;

    @GetMapping()
    public String groups(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("lectureGroups", user.getLectureGroups());
        return "groups/groups";
    }

    @GetMapping("/{id}")
    public String getExactGroup(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupRepository.findById((long) id));
        return "groups/show-group";
    }
}
