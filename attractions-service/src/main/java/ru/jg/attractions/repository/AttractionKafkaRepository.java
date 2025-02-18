package ru.jg.attractions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jg.attractions.model.AttractionKafkaEventModel;

public interface AttractionKafkaRepository extends JpaRepository<AttractionKafkaEventModel, String> {
}
