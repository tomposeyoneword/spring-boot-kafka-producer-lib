package com.travelport.kafka.client.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaProducer {

    private final String topic;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaProducer(String topic, KafkaTemplate<String, String> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {

        // Fire and forget
        // kafkaTemplate.send(topic, message);

        // If we want to get/view/verify that we sent a message
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message from Producer Library=" + message);
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message from Producer Library=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendMessage(Object message) throws JsonProcessingException {

        String json = mapper.writeValueAsString(message);
        sendMessage(json);
    }
}