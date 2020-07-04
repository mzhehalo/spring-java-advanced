package com.example.springjavaadvanced.service;

import com.example.springjavaadvanced.model.Movie;

import java.util.List;

public interface IMovieService {

    List<Movie> getAllMovies();

    Movie insertMovie(Movie movie, int directorId);

    Movie updateMovie(Movie movie);

    void deleteMovie(Integer id);

    boolean existsById(Integer id);
}
