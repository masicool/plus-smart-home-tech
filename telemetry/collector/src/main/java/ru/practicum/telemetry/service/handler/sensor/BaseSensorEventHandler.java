package ru.practicum.telemetry.service.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.model.sensor.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import static ru.practicum.telemetry.kafka.KafkaTopic.SENSORS_EVENTS;

@Slf4j
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    private final EventKafkaProducer producer;

    protected BaseSensorEventHandler(EventKafkaProducer producer) {
        this.producer = producer;
    }

    @Override
    public void handle(SensorEvent event) {
        if (!event.getType().equals(getEventType())) {
            throw new IllegalArgumentException("Event type mismatch");
        }

        T payload = mapToAvro(event);

        SensorEventAvro avro = SensorEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setId(event.getId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        log.info("Sensor event received: {}", avro);
        producer.send(avro, event.getHubId(), event.getTimestamp(), SENSORS_EVENTS);
    }

    protected abstract T mapToAvro(SensorEvent event);
}
