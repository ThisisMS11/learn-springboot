package com.crudAPI17.crudAPI17.repository;

import com.crudAPI17.crudAPI17.entity.JournalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntity,String> {

}
