package com.illogicalparadox.illogicalParadox.controller;
import java.time.LocalDateTime;
import java.util.*;
import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.illogicalparadox.illogicalParadox.entity.JournalEntry;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/abc")
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }
    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){

        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myId}")
    public JournalEntry getjournalEnteryById(@PathVariable String myId ){

        return journalEntryService.findById(new ObjectId(myId)).orElse(null);
    }
    @DeleteMapping("delete/{myId}")
    public Boolean deleteJournalEntry(@PathVariable String myId){
        journalEntryService.deleteById(new ObjectId(myId));
        return true;
    }
    @PutMapping("update/{id}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
        JournalEntry old=journalEntryService.findById(id).orElse(null);
        if(old!=null){
            old.setName(newEntry.getName()!=null && newEntry.getName().equals("")? newEntry.getName(): old.getName());
            old.setContent(newEntry.getContent()!=null && newEntry.getContent().equals("")? newEntry.getContent(): old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;

    }
}
