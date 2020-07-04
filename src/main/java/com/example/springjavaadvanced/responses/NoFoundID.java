package com.example.springjavaadvanced.responses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such order")
public class NoFoundID extends RuntimeException{
    static {
        log.info("No such id!!!");
    }
}
