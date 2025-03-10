package ru.practicum.telemetry.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.model.sensor.SensorEvent;
import ru.practicum.telemetry.model.sensor.SensorEventType;
import ru.practicum.telemetry.model.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@Component
public class TemperatureSensorEventHandler extends BaseSensorEventHandler<TemperatureSensorAvro> {
    protected TemperatureSensorEventHandler(EventKafkaProducer producer) {
        super(producer);
    }

    @Override
    protected TemperatureSensorAvro mapToAvro(SensorEvent event) {
        TemperatureSensorEvent temperatureSensorEvent = (TemperatureSensorEvent) event;

        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(temperatureSensorEvent.getTemperatureC())
                .setTemperatureF(temperatureSensorEvent.getTemperatureF())
                .build();
    }

    @Override
    public SensorEventType getEventType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
