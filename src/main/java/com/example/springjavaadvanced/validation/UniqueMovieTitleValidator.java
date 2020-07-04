package com.example.springjavaadvanced.validation;

import com.example.springjavaadvanced.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UniqueMovieTitleValidator implements ConstraintValidator<UniqueMovieTitle, String> {

   public boolean isValid(String title, ConstraintValidatorContext context) {
      log.info("Movie with title - " + title +  " exist!");
      return true;
   }
}
