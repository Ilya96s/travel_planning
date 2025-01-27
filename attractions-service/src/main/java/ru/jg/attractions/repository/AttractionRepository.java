package ru.jg.attractions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jg.attractions.model.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
