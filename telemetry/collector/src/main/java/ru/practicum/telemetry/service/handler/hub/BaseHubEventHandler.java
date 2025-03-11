package ru.practicum.telemetry.service.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import static ru.practicum.telemetry.kafka.KafkaTopic.HUBS_EVENTS;

@Slf4j
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    private final EventKafkaProducer producer;

    protected BaseHubEventHandler(EventKafkaProducer producer) {
        this.producer = producer;
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
        producer.send(avro, event.getHubId(), event.getTimestamp(), HUBS_EVENTS);
    }

    protected abstract T mapToAvro(HubEvent event);
}
