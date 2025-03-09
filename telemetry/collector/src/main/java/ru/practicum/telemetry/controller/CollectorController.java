package ru.practicum.telemetry.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.practicum.telemetry.model.hub.HubEventType;
import ru.practicum.telemetry.model.sensor.SensorEvent;
import ru.practicum.telemetry.model.sensor.SensorEventType;
import ru.practicum.telemetry.service.handler.hub.HubEventHandler;
import ru.practicum.telemetry.service.handler.sensor.SensorEventHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/events")
public class CollectorController {
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    public CollectorController(List<SensorEventHandler> sensorEventHandlers, List<HubEventHandler> hubEventHandlers) {
        this.sensorEventHandlers = sensorEventHandlers.stream().collect(Collectors.toMap(SensorEventHandler::getEventType, Function.identity()));
        this.hubEventHandlers = hubEventHandlers.stream().collect(Collectors.toMap(HubEventHandler::getEventType, Function.identity()));
    }

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        if (sensorEventHandlers.containsKey(event.getType())) {
            log.info("Collecting sensor event: {}", event);
            sensorEventHandlers.get(event.getType()).handle(event);
        } else {
            log.error("Unknown sensor type: {}", event.getType());
            throw new IllegalArgumentException("Unknown event type: " + event.getType());
        }
    }

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        if (hubEventHandlers.containsKey(event.getType())) {
            log.info("Collecting hub event: {}", event);
            hubEventHandlers.get(event.getType()).handle(event);
        } else {
            log.error("Unknown hub type: {}", event.getType());
            throw new IllegalArgumentException("Unknown event type: " + event.getType());
        }
    }
}
