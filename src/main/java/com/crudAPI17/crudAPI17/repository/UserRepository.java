package com.crudAPI17.crudAPI17.repository;

import com.crudAPI17.crudAPI17.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUserName(String username);
}
