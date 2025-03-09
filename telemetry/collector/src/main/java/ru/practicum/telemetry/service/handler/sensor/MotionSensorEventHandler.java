package ru.practicum.telemetry.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.kafka.KafkaConfig;
import ru.practicum.telemetry.model.sensor.MotionSensorEvent;
import ru.practicum.telemetry.model.sensor.SensorEvent;
import ru.practicum.telemetry.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Component
public class MotionSensorEventHandler extends BaseSensorEventHandler<MotionSensorAvro> {
    protected MotionSensorEventHandler(EventKafkaProducer producer, KafkaConfig kafkaConfig) {
        super(producer, kafkaConfig);
    }

    @Override
    protected MotionSensorAvro mapToAvro(SensorEvent event) {
        MotionSensorEvent motionSensorEvent = (MotionSensorEvent) event;

        return MotionSensorAvro.newBuilder()
                .setMotion(motionSensorEvent.getMotion())
                .setLinkQuality(motionSensorEvent.getLinkQuality())
                .setVoltage(motionSensorEvent.getVoltage())
                .build();
    }

    @Override
    public SensorEventType getEventType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
