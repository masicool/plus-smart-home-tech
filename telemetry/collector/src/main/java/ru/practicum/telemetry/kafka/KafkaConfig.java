package ru.practicum.telemetry.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Getter
@Setter
@Component
@ConfigurationProperties("collector.kafka")
public class KafkaConfig {
    Map<String, String> topics;
    Properties properties;
}
