package com.illogicalparadox.illogicalParadox.controller;

import java.time.LocalDateTime;
import java.util.*;
import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.service.JournalEntryService;
import com.illogicalparadox.illogicalParadox.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.illogicalparadox.illogicalParadox.entity.JournalEntry;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/abc")
    public List<User> getAllUsers(){
        return userService.getAll();
    }
    @PostMapping("/create")
    public void createUser (@RequestBody User user){
        userService.saveEntry(user);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUser (@RequestBody User user){
        User userInDb =userService.findByUsername(user.getUsername());
        if(userInDb!=null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
