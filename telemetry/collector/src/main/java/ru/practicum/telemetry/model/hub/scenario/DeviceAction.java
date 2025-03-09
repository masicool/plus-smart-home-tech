package ru.practicum.telemetry.model.hub.scenario;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

// действие, которое должно быть выполнено в рамках сценария
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAction {
    @NotNull
    String sensorId; // идентификатор датчика, связанного с действием
    @NotNull
    DeviceActionType type; //  возможный тип действия при срабатывании условия активации сценария
    Integer value; // значение, связанное с действием
}
