package ru.jg.travelplans.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttractionNotFoundException extends RuntimeException {

    private final String code;

    public AttractionNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
