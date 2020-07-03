package com.example.springjavaadvanced.validation;

import com.example.springjavaadvanced.model.Movie;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MovieValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Movie.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Movie movie = (Movie) target;
        if (!StringHelper.isNullOrEmptyString(movie.getTitle())) {
            char firstChar = movie.getTitle().charAt(0);
            if (firstChar < 65 || firstChar > 90) {
                errors.rejectValue("title", "movie.title.capital-letter", "First letter in Movie title should be CAPITAL");
            }
        }
    }
}
