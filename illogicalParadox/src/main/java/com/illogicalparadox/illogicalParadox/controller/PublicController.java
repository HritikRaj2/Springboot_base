package com.illogicalparadox.illogicalParadox.controller;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){return "i am Alive";}
    @PostMapping("/create")
    public void createUser (@RequestBody User user){
        userService.saveNewUser(user);
    }
}

