package com.crudAPI17.crudAPI17.service;

import com.crudAPI17.crudAPI17.entity.JournalEntity;
import com.crudAPI17.crudAPI17.entity.User;
import com.crudAPI17.crudAPI17.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntity journalEntity, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepository.save(journalEntity);
            user.getJournalEnteries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void saveEntry(JournalEntity journalEntity){
        journalEntryRepository.save(journalEntity);
    }


    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findJournalById(ObjectId id){
        return journalEntryRepository.findById(String.valueOf(id));
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEnteries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(String.valueOf(id));
    }
}
