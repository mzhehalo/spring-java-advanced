package com.example.springjavaadvanced.controller;

import com.example.springjavaadvanced.model.Movie;
import com.example.springjavaadvanced.repository.MovieRepository;
import com.example.springjavaadvanced.responses.NoFoundID;
import com.example.springjavaadvanced.service.IMovieService;
import com.example.springjavaadvanced.validation.MovieValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private MovieValidator movieValidator;

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movie createMovie(@RequestBody @Valid Movie movie) {
        return movieService.insertMovie(movie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Movie updateMovie(@RequestBody Movie movie, @PathVariable Integer id) {
//        Optional<Movie> movieOptional = movies.stream()
//                .filter(movie1 -> movie1.getId() == id)
//                .findFirst();
//        if (movieOptional.isPresent()) {
//            Movie movieFromList = movieOptional.get();
//            movies.set(movies.indexOf(movieFromList), movie);
//        } else {
//            movie.setId(id);
//            movies.add(movie);
//        }
//        return movie;

        movie.setId(id);
        return movieService.updateMovie(movie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
//        Optional<Movie> optionalDeleteMovie = movies.stream()
//                .filter(movie -> movie.getId() == id).findFirst();
//        if (optionalDeleteMovie.isPresent()) {
//            Movie movie = optionalDeleteMovie.get();
//            movies.remove(movie);
//        } else {
//            throw new NoFoundID();
//        }

        if (movieService.existsById(id)) {
            movieService.deleteMovie(id);
        } else {
            throw new NoFoundID();
        }
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(movieValidator);
    }
}
