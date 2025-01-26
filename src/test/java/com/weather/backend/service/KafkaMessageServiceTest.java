package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.WeatherAppMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class KafkaMessageServiceTest {

    @InjectMocks
    private KafkaMessageService kafkaMessageService;

    @Mock
    private KafkaTemplate<String, WeatherAppMessage> kafkaTemplate;

    @Mock
    private AppProps kafkaProps;

    private static final String TOPIC = "weather-topic";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(kafkaProps.getKafkaTopic()).thenReturn(TOPIC);
    }

    @Test
    void testSendMessage_Successful() {
        WeatherAppMessage message = new WeatherAppMessage("London");
        kafkaMessageService.sendMessage(message);
        verify(kafkaTemplate, times(1)).send(TOPIC, message.getCity(), message);
    }

    @Test
    void testSendMessage_ExceptionHandling() {
        WeatherAppMessage message = new WeatherAppMessage("London");
        doThrow(new RuntimeException("Kafka error")).when(kafkaTemplate).send(TOPIC, message.getCity(), message);
        assertDoesNotThrow(() -> kafkaMessageService.sendMessage(message));
        verify(kafkaTemplate, times(1)).send(TOPIC, message.getCity(), message);
    }
}

