package ru.jg.travelplans.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TravelPlanNotFoundException extends RuntimeException {

    public TravelPlanNotFoundException(String message) {
        super(message);
    }
}
