package com.illogicalparadox.illogicalParadox.service;

import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        User user=userService.findByUsername(userName);
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);

    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);

    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public void deleteById(Object id, String userName){
        try {
            User user = userService.findByUsername(userName);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveNewUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch(Exception e){
            System.out.println(e);
            //logger.info("HHHAHHAHAH");
            throw new RuntimeException("An Error occured while deleting the entries");
        }
    }

}
