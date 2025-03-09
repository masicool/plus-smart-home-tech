package ru.practicum.telemetry.model.hub.device;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.telemetry.model.hub.HubEvent;
import ru.practicum.telemetry.model.hub.HubEventType;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceRemovedEvent extends HubEvent {
    @NotNull
    String id; // идентификатор удаленного устройства

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
