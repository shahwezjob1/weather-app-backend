package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.WeatherAppMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageService implements MessageService {

    private final KafkaTemplate<String, WeatherAppMessage> kafkaTemplate;

    private final AppProps props;

    @Override
    public void sendMessage(WeatherAppMessage message) {
        try {
            log.debug("KafkaMessageService::sendMessage::" + message);
            kafkaTemplate.send(props.getKafkaTopic(), message.getCity(), message);
        } catch (Exception e) {
            log.error("Exception when trying to send kafka message = " + e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
