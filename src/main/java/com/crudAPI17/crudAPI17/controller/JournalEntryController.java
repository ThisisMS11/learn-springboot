package com.crudAPI17.crudAPI17.controller;
import com.crudAPI17.crudAPI17.entity.User;
import com.crudAPI17.crudAPI17.service.JournalEntryService;
import com.crudAPI17.crudAPI17.entity.JournalEntity;
import com.crudAPI17.crudAPI17.service.UserService;
import org.apache.catalina.connector.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry, @PathVariable String userName) {
        try{
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<JournalEntity>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userName}")
    public List<JournalEntity> getAllJournalEnteriesByUser(@PathVariable String userName){
        return journalEntryService.getAll();
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntity> entity = journalEntryService.findJournalById(myId);

        if(entity.isPresent()){
            return new ResponseEntity<JournalEntity>(entity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @PathVariable String userName, @RequestBody JournalEntity newEntry){
        JournalEntity old = journalEntryService.findJournalById(myId).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        journalEntryService.saveEntry(old);
        return new ResponseEntity<>(newEntry, HttpStatus.OK);
    }
}
