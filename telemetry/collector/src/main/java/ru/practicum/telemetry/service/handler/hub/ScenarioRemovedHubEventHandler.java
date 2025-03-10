package ru.practicum.telemetry.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.practicum.telemetry.model.hub.HubEventType;
import ru.practicum.telemetry.model.hub.scenario.ScenarioRemovedEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
public class ScenarioRemovedHubEventHandler extends BaseHubEventHandler<ScenarioRemovedEventAvro> {
    protected ScenarioRemovedHubEventHandler(EventKafkaProducer producer) {
        super(producer);
    }

    @Override
    protected ScenarioRemovedEventAvro mapToAvro(HubEvent event) {
        ScenarioRemovedEvent scenarioRemovedEvent = (ScenarioRemovedEvent) event;

        return ScenarioRemovedEventAvro.newBuilder()
                .setName(scenarioRemovedEvent.getName())
                .build();
    }

    @Override
    public HubEventType getEventType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
