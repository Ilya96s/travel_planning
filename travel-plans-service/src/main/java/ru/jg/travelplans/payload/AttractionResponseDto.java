package ru.jg.travelplans.payload;

public record AttractionResponseDto(Long id,
                                    String name,
                                    String city,
                                    String description) {
}
