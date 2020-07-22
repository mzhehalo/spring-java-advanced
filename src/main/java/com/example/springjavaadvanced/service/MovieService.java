package com.example.springjavaadvanced.service;

import com.example.springjavaadvanced.dtos.MovieDTO;
import com.example.springjavaadvanced.model.Movie;
import com.example.springjavaadvanced.repository.DirectorRepository;
import com.example.springjavaadvanced.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class MovieService implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    public MovieDTO getMovies(PageRequest pageRequest) {
        Page<Movie> moviePages = movieRepository.findAll(pageRequest);
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovies(moviePages.getContent());
        movieDTO.setEmpty(moviePages.isEmpty());
        movieDTO.setLast(moviePages.isLast());
        movieDTO.setPagesCount(moviePages.getTotalPages());
        movieDTO.setTotalElements(moviePages.getNumberOfElements());
        return movieDTO;
    }

    @Override
    public Movie insertMovie(Movie movie, int directorId) {
        if (movieRepository.findByTitle(movie.getTitle()).isPresent()) {
            log.info("Movie with title - " + movie.getTitle() + " exist!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This movie title already exist");
        }
        directorRepository.findById(directorId).ifPresent(director -> {
            movieRepository.save(movie);
        });
        return movie;
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
