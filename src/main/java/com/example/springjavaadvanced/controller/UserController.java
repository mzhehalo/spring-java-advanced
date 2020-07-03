package com.example.springjavaadvanced.controller;

import com.example.springjavaadvanced.model.User;
import com.example.springjavaadvanced.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    public void setUserRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//        System.out.println(userRepository.findAll() + "userRepository-------------------1");
//        System.out.println(userRepository.getClass() + "userRepository-------------------2");
//    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
//        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }
}
