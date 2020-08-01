package com.example.springjavaadvanced.controller;

import com.example.springjavaadvanced.dtos.MovieDTO;
import com.example.springjavaadvanced.model.Director;
import com.example.springjavaadvanced.model.Movie;
import com.example.springjavaadvanced.service.IMovieService;
import com.example.springjavaadvanced.validation.MovieValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
@WebMvcTest(MovieController.class)
@ContextConfiguration(classes = MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MovieController movieController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMovieService movieService;

    @MockBean
    private MovieValidator movieValidator;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        when(movieValidator.supports(any())).thenReturn(true);
    }

    @Test
    public void getMoviesWithoutParametersMustRespondWithMovieList() throws Exception {

        Movie movie = new Movie();
        movie.setId(1);

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTotalElements(1);
        movieDTO.setMovies(Arrays.asList(movie));

        BDDMockito.given(movieService.getMovies(any(PageRequest.class))).willReturn(movieDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1));
    }

    @Test
    public void createMoviesWithParametersMovieAndDirectorId() throws Exception {

        Director director = new Director();
        director.setId(2);
        director.setFirstName("Mac");

        Movie movie = new Movie();
        movie.setId(2);
        movie.setTitle("TwoTwo");
        movie.setDescription("TwoTwo");
        movie.setDuration(2);
        movie.setDirector(director);

        BDDMockito.given(movieService.insertMovie(any(Movie.class), any(Integer.class))).willReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.post("/movies/2")
                .content(asJsonString(movie))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TwoTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("TwoTwo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(2));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
