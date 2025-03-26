package com.illogicalparadox.illogicalParadox.controller;
import java.time.LocalDateTime;

import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.service.JournalEntryService;
import com.illogicalparadox.illogicalParadox.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user=userService.findByUsername(userName);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){

        try{

            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myId}")
    public JournalEntry getjournalEnteryById(@PathVariable String myId ){

        return journalEntryService.findById(new ObjectId(myId)).orElse(null);
    }
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?>  deleteJournalEntry(@PathVariable ObjectId myId,@PathVariable String userName){
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("update/{userName}/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry, @PathVariable String userName){
        JournalEntry old=journalEntryService.findById(id).orElse(null);
        if(old!=null){
            old.setName(newEntry.getName()!=null && newEntry.getName().equals("")? newEntry.getName(): old.getName());
            old.setContent(newEntry.getContent()!=null && newEntry.getContent().equals("")? newEntry.getContent(): old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
