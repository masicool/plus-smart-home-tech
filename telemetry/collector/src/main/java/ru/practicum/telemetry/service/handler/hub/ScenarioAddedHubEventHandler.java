package ru.practicum.telemetry.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.telemetry.kafka.EventKafkaProducer;
import ru.practicum.telemetry.kafka.KafkaConfig;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.practicum.telemetry.model.hub.HubEventType;
import ru.practicum.telemetry.model.hub.scenario.DeviceAction;
import ru.practicum.telemetry.model.hub.scenario.ScenarioAddedEvent;
import ru.practicum.telemetry.model.hub.scenario.ScenarioCondition;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;

@Component
public class ScenarioAddedHubEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {
    protected ScenarioAddedHubEventHandler(EventKafkaProducer producer, KafkaConfig kafkaConfig) {
        super(producer, kafkaConfig);
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEvent event) {
        ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) event;

        List<ScenarioConditionAvro> conditions = scenarioAddedEvent.getConditions().stream().map(this::mapToConditionAvro).toList();
        List<DeviceActionAvro> actions = scenarioAddedEvent.getActions().stream().map(this::mapToActionAvro).toList();

        return ScenarioAddedEventAvro.newBuilder()
                .setConditions(conditions)
                .setActions(actions)
                .setName(scenarioAddedEvent.getName())
                .build();
    }

    @Override
    public HubEventType getEventType() {
        return HubEventType.SCENARIO_ADDED;
    }

    private ScenarioConditionAvro mapToConditionAvro(ScenarioCondition scenarioCondition) {
        return ScenarioConditionAvro.newBuilder()
                .setType(ConditionTypeAvro.valueOf(scenarioCondition.getType().name()))
                .setSensorId(scenarioCondition.getSensorId())
                .setValue(scenarioCondition.getValue())
                .setOperation(ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name()))
                .build();
    }

    private DeviceActionAvro mapToActionAvro(DeviceAction deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setType(ActionTypeAvro.valueOf(deviceAction.getType().name()))
                .setSensorId(deviceAction.getSensorId())
                .setValue(deviceAction.getValue())
                .build();
    }
}
