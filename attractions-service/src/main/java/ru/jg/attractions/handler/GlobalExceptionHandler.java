package ru.jg.attractions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jg.attractions.exception.AttractionNotFoundException;
import ru.jg.attractions.exception.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AttractionNotFoundException.class)
    public ErrorResponse handleAttractionNotFoundException(AttractionNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
