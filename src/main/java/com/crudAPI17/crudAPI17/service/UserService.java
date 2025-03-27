package com.crudAPI17.crudAPI17.service;

import com.crudAPI17.crudAPI17.entity.User;
import com.crudAPI17.crudAPI17.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(String.valueOf(id));
    }
}
