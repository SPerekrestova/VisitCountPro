package com.sperekrestova.visitCount.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Svetlana
 * Date: 09.01.2021
 */
@Controller
@RequestMapping("/load")
public class LoadController {

    @GetMapping("/timetable")
    public String timetable(Model model) {
        return "load-timetable";
    }

    @GetMapping("/marks")
    public String marks(Model model) {
        return "load-marks";
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        return "load-groups";
    }

}
