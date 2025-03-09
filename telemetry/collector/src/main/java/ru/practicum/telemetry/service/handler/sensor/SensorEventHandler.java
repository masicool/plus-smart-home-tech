package ru.practicum.telemetry.service.handler.sensor;

import ru.practicum.telemetry.model.sensor.SensorEvent;
import ru.practicum.telemetry.model.sensor.SensorEventType;

public interface SensorEventHandler {
    void handle(SensorEvent event);

    SensorEventType getEventType();
}
