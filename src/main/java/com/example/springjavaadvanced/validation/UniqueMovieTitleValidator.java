package com.example.springjavaadvanced.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UniqueMovieTitleValidator implements ConstraintValidator<UniqueMovieTitle, String> {

   public boolean isValid(String title, ConstraintValidatorContext context) {
      log.info("Movie with title - " + title +  " exist!");
      return true;
   }
}
