package com.example.kafkacore.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionCreatedEvent {

    private String attractionId;

    private String name;

    private String city;

    private String description;
}
