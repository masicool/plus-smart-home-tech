package ru.yandex.practicum.model.hub.scenario;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioAddedEvent extends HubEvent {
    @NotNull
    @Size(min = 3)
    String name; // название добавленного сценария
    @NotEmpty
    List<ScenarioCondition> conditions; // список условий, которые связаны со сценарием
    @NotEmpty
    List<DeviceAction> actions; // список действий, которые должны быть выполнены в рамках сценария

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
