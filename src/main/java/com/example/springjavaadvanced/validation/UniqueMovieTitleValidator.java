package com.example.springjavaadvanced.validation;

import com.example.springjavaadvanced.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueMovieTitleValidator implements ConstraintValidator<UniqueMovieTitle, String> {

   public boolean isValid(String title, ConstraintValidatorContext context) {
      return true;
   }
}
