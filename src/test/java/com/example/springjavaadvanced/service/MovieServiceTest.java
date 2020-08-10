package com.example.springjavaadvanced.service;

import com.example.springjavaadvanced.dtos.MovieDTO;
import com.example.springjavaadvanced.model.Director;
import com.example.springjavaadvanced.model.Movie;
import com.example.springjavaadvanced.repository.DirectorRepository;
import com.example.springjavaadvanced.repository.MovieRepository;
import io.jsonwebtoken.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoPostProcessor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
@WebMvcTest(MovieService.class)
@ContextConfiguration(classes = MovieService.class)
public class MovieServiceTest {

//    @Autowired
//    private MockMvc mockMvc;

    @InjectMocks
    MovieService movieService;

//    @MockBean
//    private IMovieService movieService;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Get movies")
    public void testGetMovies() {

        Movie movieOne = new Movie();
        movieOne.setTitle("One");
        movieOne.setDescription("One");
        movieOne.setDuration(1);
        Movie movieTwo = new Movie();
        Movie movieThree = new Movie();

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.unsorted());
        List<Movie> movieList = Arrays.asList(movieOne, movieTwo, movieThree);
        Page<Movie> moviePage = new PageImpl<>(movieList, pageRequest, movieList.size());

        when(movieRepository.findAll(pageRequest)).thenReturn(moviePage);

        MovieDTO movieDTOReturned = movieService.getMovies(pageRequest);
        Assert.notNull(movieDTOReturned);
    }

    @Test
    @DisplayName("Insert movie with unique title")
    public void testInsertMovieWithUniqueTitle() {
        Movie movieOne = new Movie();
        movieOne.setId(1);
        movieOne.setTitle("One");
        movieOne.setDescription("One");
        movieOne.setDuration(1);

        Director directorOne = new Director();
        directorOne.setId(1);

        Movie createdMovie = movieService.insertMovie(movieOne, 1);
        Assertions.assertThat(createdMovie).isNotNull();
    }

    @Test
    @DisplayName("Insert movie with already exist title")
    public void testInsertMovieWithAlreadyExistTitle() {
        Movie movieOne = new Movie();
        movieOne.setTitle("One");
        movieOne.setDescription("One");
        movieOne.setDuration(1);

        BDDMockito.when(movieRepository.findByTitle(movieOne.getTitle())).thenReturn(Optional.of(movieOne));

        assertThrows(ResponseStatusException.class,  () -> {
            movieService.insertMovie(movieOne, 1);
        });
    }

    @Test
    @DisplayName("Update movie")
    public void testUpdateMovie(){
        Movie movieOne = new Movie();
        movieOne.setId(1);
        movieOne.setTitle("One");
        movieOne.setDescription("One");
        movieOne.setDuration(1);

        Director directorOne = new Director();
        directorOne.setId(1);

        BDDMockito.when(movieRepository.save(movieOne)).thenReturn(movieOne);

        Movie updatedMovie = movieService.updateMovie(movieOne);
        Assertions.assertThat(updatedMovie).isNotNull();
    }

    @Test
    @DisplayName("Delete movie")
    public void testDeleteMovie(){
       movieService.deleteMovie(1);
    }

    @Test
    @DisplayName("Exist by id")
    public void testExistById(){
        BDDMockito.when(movieRepository.existsById(1)).thenReturn(true);
        boolean existsById = movieService.existsById(1);
        Assertions.assertThat(existsById).isEqualTo(true);
    }
}