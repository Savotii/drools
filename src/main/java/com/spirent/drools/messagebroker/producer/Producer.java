package com.spirent.drools.messagebroker.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author ysavi2
 * @since 06.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${kafka.topics.failure_topic}")
    private String rulesFailureTopic;

    @Value(value = "${kafka.topics.success_topic}")
    private String rulesSuccessTopic;

    public void sendFailureMessage(String message) {
        if (Objects.isNull(message)) {
            return; //nothing to send.
        }
        log.info(String.format("#### -> Producing  failure message -> %s", message));
        this.kafkaTemplate.send(rulesFailureTopic, message);
    }

    public void sendSuccessMessage(String message) {
        if (Objects.isNull(message)) {
            return;
        }
        log.info(String.format("#### -> Producing  success message -> %s", message));
        this.kafkaTemplate.send(rulesSuccessTopic, message);
    }
}
