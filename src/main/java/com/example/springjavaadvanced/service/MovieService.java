package com.example.springjavaadvanced.service;

import com.example.springjavaadvanced.model.Movie;
import com.example.springjavaadvanced.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovieService implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie insertMovie(Movie movie) {
        if(!movieRepository.findByTitle(movie.getTitle()).isPresent()) {
            return movieRepository.save(movie);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This movie title already exist");
        }
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return movieRepository.existsById(id);
    }


}
