package com.illogicalparadox.illogicalParadox.service;

import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.repository.JournalEntryRepository;
import com.illogicalparadox.illogicalParadox.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void saveEntry(User user){
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
}

