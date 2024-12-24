package com.weather.backend.service;

import com.weather.backend.domain.WeatherAppMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageService implements MessageService {

    private final KafkaTemplate<String, WeatherAppMessage> kafkaTemplate;

    @Value("${app.kafka.producer.topic}")
    String topic;

    @Override
    public void sendMessage(WeatherAppMessage message) {
        try {
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            log.error("Exception when trying to send kafka message = " + e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
