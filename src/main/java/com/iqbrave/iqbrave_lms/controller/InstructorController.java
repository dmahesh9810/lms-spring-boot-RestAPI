package com.iqbrave.iqbrave_lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @GetMapping("/dashboard")
    public String instructorDashboard() {
        return "Welcome INSTRUCTOR!";
    }
}
