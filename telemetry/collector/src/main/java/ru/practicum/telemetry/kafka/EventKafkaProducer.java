package ru.practicum.telemetry.kafka;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class EventKafkaProducer {
    private final KafkaProducer<String, SpecificRecordBase> producer;

    public EventKafkaProducer(KafkaConfig kafkaConfig) {
        producer = new KafkaProducer<>(kafkaConfig.getProperties());
    }

    public void send(SpecificRecordBase event, String hubId, Instant timestamp, String topic) {
        ProducerRecord<String, SpecificRecordBase> record =
                new ProducerRecord<>(
                        topic,
                        null,
                        timestamp.toEpochMilli(),
                        hubId,
                        event);
        log.info("Sending event {} at hub ID {} to topic {}", event.getClass().getSimpleName(), hubId, topic);
        producer.send(record);
    }

    @PreDestroy
    public void close() {
        log.info("Shutting down producer");
        producer.flush();
        producer.close(Duration.ofSeconds(5));
    }
}
