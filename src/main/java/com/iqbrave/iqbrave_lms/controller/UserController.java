package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}

