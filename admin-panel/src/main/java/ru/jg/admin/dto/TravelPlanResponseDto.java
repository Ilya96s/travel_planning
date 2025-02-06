package ru.jg.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlanResponseDto {

    private Long planId;

    private Long userId;

    private AttractionResponseDto attraction;

    private String visitDate;

}
