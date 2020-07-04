package com.example.springjavaadvanced.service;

import com.example.springjavaadvanced.model.Director;
import com.example.springjavaadvanced.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService implements IDirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public Director insertDirector(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }
}
