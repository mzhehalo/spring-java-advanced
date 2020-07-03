package com.example.springjavaadvanced.responses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such order")
public class NoFoundID extends RuntimeException{
}
