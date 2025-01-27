package ru.jg.attractions.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttractionNotFoundException extends RuntimeException {

    public AttractionNotFoundException(String message) {
        super(message);
    }
}
