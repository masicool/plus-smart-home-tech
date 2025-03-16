package ru.practicum.telemetry.service.handler.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

public interface SensorEventHandler {
    void handle(SensorEventProto event);

    SensorEventProto.PayloadCase getEventType();
}
