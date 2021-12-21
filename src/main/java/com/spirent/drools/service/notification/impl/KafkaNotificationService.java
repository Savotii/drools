package com.spirent.drools.service.notification.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spirent.drools.messagebroker.producer.Producer;
import com.spirent.drools.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaNotificationService implements NotificationService {
    private final Producer kafkaProducer;
    private final ObjectMapper mapper;

    @Override
    public void send(Object obj) {
        try {
            kafkaProducer.sendFailureMessage(mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.error("[Kafka notification service] Conversion to JSON has been failed. ", e);
        }
    }
}
