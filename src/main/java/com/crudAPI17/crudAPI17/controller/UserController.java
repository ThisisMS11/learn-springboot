package com.crudAPI17.crudAPI17.controller;

import com.crudAPI17.crudAPI17.entity.User;
import com.crudAPI17.crudAPI17.repository.UserRepository;
import com.crudAPI17.crudAPI17.service.UserService;
import com.fasterxml.jackson.datatype.jdk8.OptionalLongDeserializer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateUserByUserName(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
