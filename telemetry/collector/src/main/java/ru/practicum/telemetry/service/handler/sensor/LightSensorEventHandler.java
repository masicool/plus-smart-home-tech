package ru.practicum.telemetry.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.model.sensor.LightSensorEvent;
import ru.practicum.telemetry.model.sensor.SensorEvent;
import ru.practicum.telemetry.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@Component
public class LightSensorEventHandler extends BaseSensorEventHandler<LightSensorAvro> {
    protected LightSensorEventHandler(EventKafkaProducer producer) {
        super(producer);
    }

    @Override
    protected LightSensorAvro mapToAvro(SensorEvent event) {
        LightSensorEvent lightSensorEvent = (LightSensorEvent) event;

        return LightSensorAvro.newBuilder()
                .setLinkQuality(lightSensorEvent.getLinkQuality())
                .setLuminosity(lightSensorEvent.getLuminosity())
                .build();
    }

    @Override
    public SensorEventType getEventType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
