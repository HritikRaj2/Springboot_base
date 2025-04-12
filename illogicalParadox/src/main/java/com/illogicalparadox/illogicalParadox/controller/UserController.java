package com.illogicalparadox.illogicalParadox.controller;
import com.illogicalparadox.illogicalParadox.api.response.WeatherResponse;
import com.illogicalparadox.illogicalParadox.service.WeatherService;
import org.springframework.security.core.Authentication;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.repository.UserRepository;
import com.illogicalparadox.illogicalParadox.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser (@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User userInDb =userService.findByUsername(user.getUsername());
        if(userInDb!=null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?>  deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/mapp")
    public ResponseEntity<?> greeting(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse=weatherService.getWeather("Mumbai");
        String greeting="";
        if(weatherResponse!=null){
            greeting=",Weather "+ weatherResponse.getCurrent().getFeelslike()+"" + "\nTemperature is "+weatherResponse.getCurrent().getTemperature();
        }



        return new ResponseEntity<>("Hi " + authentication.getName()+greeting ,HttpStatus.OK);
    }
}
