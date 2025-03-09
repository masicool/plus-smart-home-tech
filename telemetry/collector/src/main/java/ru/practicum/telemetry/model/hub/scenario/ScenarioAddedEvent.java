package ru.practicum.telemetry.model.hub.scenario;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.practicum.telemetry.model.hub.HubEventType;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioAddedEvent extends HubEvent {
    @NotNull
    @Size(min = 3)
    String name; // название добавленного сценария
    @NotNull
    @NotEmpty
    List<ScenarioCondition> conditions; // список условий, которые связаны со сценарием
    @NotNull
    @NotEmpty
    List<DeviceAction> actions; // список действий, которые должны быть выполнены в рамках сценария

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
