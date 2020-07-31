package com.example.springjavaadvanced.controller;

import com.example.springjavaadvanced.dtos.MovieDTO;
import com.example.springjavaadvanced.model.Director;
import com.example.springjavaadvanced.model.Movie;
import com.example.springjavaadvanced.service.IMovieService;
import com.example.springjavaadvanced.validation.MovieValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.validation.annotation.Validated;

import javax.validation.*;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
@WebMvcTest(MovieController.class)
@ContextConfiguration(classes = MovieController.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {
    @Autowired
    private MovieController movieController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMovieService movieService;

    @MockBean
    private MovieValidator movieValidator;

//    @MockBean
//    private MovieRepository movieRepository;
//
//    @MockBean
//    private DirectorRepository directorRepository;

//    @Autowired
//    private Validator validator;

//    @Before
//    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

//    @Before
//    public void when(requestValidatorMock.supports(any())).thenReturn(true);

    @Test
    public void getMoviesWithoutParametersMustRespondWithMovieList() throws Exception {

        Movie movie = new Movie();
        movie.setId(1);

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTotalElements(1);
        movieDTO.setMovies(Arrays.asList(movie));

        BDDMockito.given(movieService.getMovies(any(PageRequest.class))).willReturn(movieDTO);

//        MovieDTO movies = movieService.getMovies(null);
//        Assertions.assertThat(movies.getTotalElements()).isEqualTo(2);
        mockMvc.perform(MockMvcRequestBuilders.get("/movies").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1));
    }

    @Test
    public void createMoviesWithParametersMovieAndDirectorId() throws Exception {
//        Director director = new Director();
//        director.setId(2);

        Movie movie = new Movie();
//        movie.setId(2);
        movie.setTitle("TwoTwo");
        movie.setDescription("TwoTwo");
        movie.setDuration(2);
//        movie.setDirector(director);

//        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
//        assertFalse(violations.isEmpty());

//        Movie isMovieCreated = movieService.insertMovie(movie, 2);
//        Assert.assertNotNull(isMovieCreated);
//        Assert.

        ObjectMapper objectMapper = new ObjectMapper();
        String movieString = objectMapper.writeValueAsString(movie);
//        when(movieService.insertMovie(any(Movie.class), any(Integer.class))).thenReturn(movie);
        BDDMockito.when(movieService.insertMovie(any(Movie.class), any(Integer.class))).thenReturn(movie);


//        when(movieService.insertMovie(movie, 2)).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.post("/movies/2")
                .content(movieString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        )


//        .content(String.valueOf(movie))
//        .content(movieString)
//        .content("{"title": "two", "description": "two", "duration": 2}")
//        )
                .andExpect(status().isCreated());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("two"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("two"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(2));
    }

}
