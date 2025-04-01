package com.illogicalparadox.illogicalParadox.controller;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.service.JournalEntryService;
import com.illogicalparadox.illogicalParadox.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUsername(userName);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getjournalEnteryById(@PathVariable ObjectId myId ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUsername(userName);
//        List<JournalEntry> journalEntries= user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        List<JournalEntry> journalEntries = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if(!journalEntries.isEmpty()){
            Optional<JournalEntry> journalEntry= journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?>  deleteJournalEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//correct it after some time

//    @PutMapping("update/{id}")
//    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName=authentication.getName();
//        JournalEntry old=journalEntryService.findById(id).orElse(null);
//        List<JournalEntry> journalEntries = user.getJournalEntries()
//                .stream()
//                .filter(x -> x.getId().equals(id))
//                .collect(Collectors.toList());
//        if(!journalEntries.isEmpty()){
//            Optional<JournalEntry> journalEntry= journalEntryService.findById(id);
//            if(journalEntry.isPresent()){
//                old.setName(newEntry.getName()!=null && newEntry.getName().equals("")? newEntry.getName(): old.getName());
//                old.setContent(newEntry.getContent()!=null && newEntry.getContent().equals("")? newEntry.getContent(): old.getContent());
//                journalEntryService.saveEntry(old);
//                return new ResponseEntity<>(old,HttpStatus.OK);
//            }
//
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }
}