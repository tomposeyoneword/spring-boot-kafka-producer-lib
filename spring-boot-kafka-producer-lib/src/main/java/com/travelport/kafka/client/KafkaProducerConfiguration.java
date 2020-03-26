package com.travelport.kafka.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;

import com.travelport.kafka.client.producer.KafkaProducer;

@Configuration
@PropertySource("classpath:application.producer.properties")
public class KafkaProducerConfiguration {

    @Bean
    public KafkaProducer producer(
            @Value(value = "${message.topic.name}") String topic,
            KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaProducer(topic, kafkaTemplate);
    }
}
