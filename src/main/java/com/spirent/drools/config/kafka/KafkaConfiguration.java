package com.spirent.drools.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ysavi2
 * @since 06.12.2021
 */
@Configuration
public class KafkaConfiguration {

    @Value(value = "${spring.kafka.url}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topics.failure_topic}")
    private String rulesFailureTopic;

    @Value(value = "${kafka.topics.success_topic}")
    private String rulesSuccessTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic getDroolsFailureTopic() {
        return new NewTopic(rulesFailureTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic getDroolsSuccessTopic() {
        return new NewTopic(rulesSuccessTopic, 1, (short) 1);
    }
}
