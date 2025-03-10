package ru.practicum.telemetry.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.practicum.telemetry.model.hub.HubEventType;
import ru.practicum.telemetry.model.hub.device.DeviceAddedEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Component
public class DeviceRemovedHubEventHandler extends BaseHubEventHandler<DeviceRemovedEventAvro> {
    protected DeviceRemovedHubEventHandler(EventKafkaProducer producer) {
        super(producer);
    }

    @Override
    protected DeviceRemovedEventAvro mapToAvro(HubEvent event) {
        DeviceAddedEvent deviceAddedEvent = (DeviceAddedEvent) event;

        return DeviceRemovedEventAvro.newBuilder()
                .setId(deviceAddedEvent.getHubId())
                .build();
    }

    @Override
    public HubEventType getEventType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
