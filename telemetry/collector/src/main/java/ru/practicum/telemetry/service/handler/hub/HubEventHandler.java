package ru.practicum.telemetry.service.handler.hub;

import ru.practicum.telemetry.model.hub.HubEvent;
import ru.practicum.telemetry.model.hub.HubEventType;

public interface HubEventHandler {
    void handle(HubEvent event);

    HubEventType getEventType();
}
