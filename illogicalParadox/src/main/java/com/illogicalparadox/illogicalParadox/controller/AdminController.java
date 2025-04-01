package com.illogicalparadox.illogicalParadox.controller;


import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/allusers")
    public ResponseEntity<?> getAllUsers(){
        List<User> all=userService.getAll();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }
}
