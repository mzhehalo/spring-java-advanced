package com.example.springjavaadvanced.service;

import com.example.springjavaadvanced.model.Director;

import java.util.List;

public interface IDirectorService {

    Director insertDirector(Director director);

    List<Director> getAllDirectors();
}
