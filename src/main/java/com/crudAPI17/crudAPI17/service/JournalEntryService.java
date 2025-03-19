package com.crudAPI17.crudAPI17.service;

import com.crudAPI17.crudAPI17.entity.JournalEntity;
import com.crudAPI17.crudAPI17.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntity journalEntity){
        journalEntryRepository.save(journalEntity);
    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findJournalById(ObjectId id){
        return journalEntryRepository.findById(String.valueOf(id));
    }

    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(String.valueOf(id));
    }

//    public boolean updateJournalById(ObjectId id , JournalEntity updated){
//        journalEntryRepository.fi
//    }

}
