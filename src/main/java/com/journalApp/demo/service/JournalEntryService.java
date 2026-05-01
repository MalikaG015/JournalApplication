package com.journalApp.demo.service;
import com.journalApp.demo.entity.JournalEntry;
import com.journalApp.demo.entity.User;
import com.journalApp.demo.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


    //interface -spring will inject its implementation at runtime
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            System.out.println("11111111111111111"+ userName);
            User user=userService.findByUserName(userName);
            System.out.println("22222222222"+ userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved=journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            throw new RuntimeException("Transaction failed, rolling back changes", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id,String userName){
        boolean removed=false;
        User user=userService.findByUserName(userName);
        removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));
        if(removed){
            userService.saveEntry(user);
            journalEntryRepository.deleteById(id);
        }
        return removed;
    }
}
