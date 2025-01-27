package ru.jg.travelplans.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import ru.jg.travelplans.exception.AttractionNotFoundException;
import ru.jg.travelplans.exception.ErrorResponse;
import ru.jg.travelplans.exception.TravelPlanNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TravelPlanNotFoundException.class)
    public ErrorResponse handleTravelPlanNotFoundException(TravelPlanNotFoundException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AttractionNotFoundException.class)
    public ErrorResponse handleAttractionNotFoundException(AttractionNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ResourceAccessException.class)
    public ErrorResponse handleResourceAccessException(ResourceAccessException ex) {
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "Attraction service is unavailable. Please try again later.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RestClientException.class)
    public ErrorResponse handleRestClientException(RestClientException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
