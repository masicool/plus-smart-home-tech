package ru.practicum.telemetry.model.hub.scenario;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
// условия, которые связаны со сценарием
public class ScenarioCondition {
    String sensorId; // Идентификатор датчика, связанного с условием
    ScenarioType type; // Типы условий, которые могут использоваться в сценариях
    ScenarioOperation operation; // Операции, которые могут быть использованы в условиях
    Integer value; // Значение, используемое в условии
}
