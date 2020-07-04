package com.example.springjavaadvanced.controller;

import com.example.springjavaadvanced.model.Director;
import com.example.springjavaadvanced.service.IDirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/director")
public class DirectorController {
    @Autowired
    private IDirectorService directorService;

    @GetMapping
    public List<Director> getDirectors(){
        return directorService.getAllDirectors();
    }

    @PostMapping
    public Director createDirector(@RequestBody Director director) {
        return directorService.insertDirector(director);
    }
}
