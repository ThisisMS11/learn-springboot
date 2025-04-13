package com.crudAPI17.crudAPI17.controller;
import com.crudAPI17.crudAPI17.entity.User;
import com.crudAPI17.crudAPI17.service.JournalEntryService;
import com.crudAPI17.crudAPI17.entity.JournalEntity;
import com.crudAPI17.crudAPI17.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            journalEntryService.saveEntry(myEntry, authentication.getName());
            return new ResponseEntity<JournalEntity>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEnteriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntity> all = user.getJournalEnteries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntity> collect= user.getJournalEnteries().stream().filter(x -> x.getId().equals(myId)).toList();

        if(!collect.isEmpty()){
            Optional<JournalEntity> journalEntity = journalEntryService.findJournalById(myId);
            if(journalEntity.isPresent()){
                return new ResponseEntity<>(journalEntity.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        boolean removed = journalEntryService.deleteById(myId, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId,@RequestBody JournalEntity newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntity> collect= user.getJournalEnteries().stream().filter(x -> x.getId().equals(myId)).toList();

        if(!collect.isEmpty()) {
            JournalEntity old = journalEntryService.findJournalById(myId).orElse(null);
            if (old != null) {
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            journalEntryService.saveEntry(old);
        }
        return new ResponseEntity<>(newEntry, HttpStatus.OK);
    }
}
