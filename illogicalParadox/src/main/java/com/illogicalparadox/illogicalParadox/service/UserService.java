package com.illogicalparadox.illogicalParadox.service;

import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEndcoder=new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public void saveNewUser(User user){
        user.setPassword(passwordEndcoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);

    }
    public void saveUser(User user){

        userRepository.save(user);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveAdmin(User user) {
        try{
            user.setPassword(passwordEndcoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);
        }catch(Exception e){
            logger.warn("its not warning");
            logger.info("its not info");
        }

    }
}

