package ru.jg.travelplans.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * План путешествия
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TravelPlan {

    /**
     * Идентификатор плана
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    /**
     * Идентификатор юзера создавшего план
     */
    private Long userId;

    /**
     * Идентификатор достопримечательности
     */
    private Long attractionId;

    /**
     * Дата запланированного посещения
     */
    private String visitDate;

}
