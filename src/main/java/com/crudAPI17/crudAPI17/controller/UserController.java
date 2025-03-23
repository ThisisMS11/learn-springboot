package com.crudAPI17.crudAPI17.controller;

import com.crudAPI17.crudAPI17.entity.User;
import com.crudAPI17.crudAPI17.service.UserService;
import com.fasterxml.jackson.datatype.jdk8.OptionalLongDeserializer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try{
            userService.saveUser(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }


    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId){
        userService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUserByUserName(@RequestBody User user, @PathVariable String userName){
        User oldUser = userService.findByUserName(userName);
        if(oldUser!=null){
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
            userService.saveUser(oldUser);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
