package ru.yandex.practicum.model.hub.device;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAddedEvent extends HubEvent {
    @NotNull
    String id; // идентификатор добавленного устройства
    @NotNull
    DeviceType deviceType; // тип устройства, которое добавляется в систему

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
