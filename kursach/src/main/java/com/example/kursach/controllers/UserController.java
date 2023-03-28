package com.example.kursach.controllers;

import com.example.kursach.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
}
