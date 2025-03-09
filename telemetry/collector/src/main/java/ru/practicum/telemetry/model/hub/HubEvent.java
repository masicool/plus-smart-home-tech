package ru.practicum.telemetry.model.hub;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.telemetry.model.hub.device.DeviceAddedEvent;
import ru.practicum.telemetry.model.hub.device.DeviceRemovedEvent;
import ru.practicum.telemetry.model.hub.scenario.ScenarioAddedEvent;
import ru.practicum.telemetry.model.hub.scenario.ScenarioRemovedEvent;

import java.time.Instant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = HubEventType.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DEVICE_ADDED"),
        @JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DEVICE_REMOVED"),
        @JsonSubTypes.Type(value = ScenarioAddedEvent.class, name = "SCENARIO_ADDED"),
        @JsonSubTypes.Type(value = ScenarioRemovedEvent.class, name = "SCENARIO_REMOVED")
})
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class HubEvent {
    @NotNull
    String hubId; // Идентификатор хаба, связанный с событием
    Instant timestamp = Instant.now(); // Временная метка события. По умолчанию устанавливается текущее время.

    public abstract HubEventType getType(); // тип события хаба
}
