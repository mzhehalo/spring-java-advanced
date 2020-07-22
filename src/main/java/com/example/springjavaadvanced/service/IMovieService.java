package com.example.springjavaadvanced.service;

import com.example.springjavaadvanced.dtos.MovieDTO;
import com.example.springjavaadvanced.model.Movie;
import org.springframework.data.domain.PageRequest;

public interface IMovieService {

    MovieDTO getMovies(PageRequest pageRequest);

    Movie insertMovie(Movie movie, int directorId);

    Movie updateMovie(Movie movie);

    void deleteMovie(Integer id);

    boolean existsById(Integer id);
}
