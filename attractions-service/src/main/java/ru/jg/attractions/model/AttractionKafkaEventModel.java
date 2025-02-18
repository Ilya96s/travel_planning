package ru.jg.attractions.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attraction_events")
public class AttractionKafkaEventModel {

    @Id
    private String attractionId;

    private String name;

    private String city;

    private String description;
}
