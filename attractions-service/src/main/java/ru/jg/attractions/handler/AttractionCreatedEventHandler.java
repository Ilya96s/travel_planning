package ru.jg.attractions.handler;


import com.example.kafkacore.event.AttractionCreatedEvent;
import com.example.kafkacore.exception.NonRetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.jg.attractions.model.AttractionKafkaEventModel;
import ru.jg.attractions.repository.AttractionKafkaRepository;

@Slf4j
@Component
@KafkaListener(topics = "attraction-created-event-topic")
@RequiredArgsConstructor
public class AttractionCreatedEventHandler {

    private final AttractionKafkaRepository attractionKafkaRepository;

    @Transactional
    @KafkaHandler
    public void handle(@Payload AttractionCreatedEvent event, @Header("attractionId") String attractionId) {
        log.info("Received event {}", event);

        if (attractionKafkaRepository.findById(attractionId).isPresent()) {
            log.info("Attraction with id {} already exists", attractionId);
            return;
        }

        try {
            attractionKafkaRepository.save(
                    new AttractionKafkaEventModel(event.getAttractionId(),
                            event.getName(),
                            event.getCity(),
                            event.getDescription())
            );

            log.info("Creating attraction kafka model with id {}", attractionId);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new NonRetryableException(e);
        }
    }

}
