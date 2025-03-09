package ru.practicum.telemetry.service.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.kafka.KafkaConfig;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Slf4j
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    private final EventKafkaProducer producer;
    private final KafkaConfig kafkaConfig;

    protected BaseHubEventHandler(EventKafkaProducer producer, KafkaConfig kafkaConfig) {
        this.producer = producer;
        this.kafkaConfig = kafkaConfig;
    }

    @Override
    public void handle(HubEvent event) {
        if (!event.getType().equals(getEventType())) {
            throw new IllegalArgumentException("Event type mismatch");
        }

        T payload = mapToAvro(event);

        HubEventAvro avro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        log.info("Hub event received: {}", avro);
        producer.send(avro, event.getHubId(), event.getTimestamp(), kafkaConfig.getTopics().get("hubs-events"));
    }

    protected abstract T mapToAvro(HubEvent event);
}
