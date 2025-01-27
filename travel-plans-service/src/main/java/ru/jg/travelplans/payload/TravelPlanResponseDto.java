package ru.jg.travelplans.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlanResponseDto {

    private Long planId;

    private Long userId;

    private AttractionResponseDto attraction;

    private String visitDate;
}
