package com.example.springjavaadvanced.controller;

import com.example.springjavaadvanced.dtos.AuthenticateRequest;
import com.example.springjavaadvanced.dtos.AuthenticationResponse;
import com.example.springjavaadvanced.model.User;
import com.example.springjavaadvanced.repository.UserRepository;
import com.example.springjavaadvanced.service.IUserService;
import com.example.springjavaadvanced.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

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
    public String registerUser (@RequestBody User user) {
        return iUserService.createUser(user);
    }

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public AuthenticationResponse authenticate(@RequestBody AuthenticateRequest authenticateRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),
                authenticateRequest.getPassword()));
        String token = jwtService.generateToken(authenticateRequest.getUsername());
        return new AuthenticationResponse(token);
    }
}
