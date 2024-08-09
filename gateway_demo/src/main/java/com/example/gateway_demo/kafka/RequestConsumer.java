package com.example.gateway_demo.kafka;

import com.example.gateway_demo.entity.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RequestConsumer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public RequestConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "requests-topic", groupId = "gateway-group")
    public void listen(String message) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Request request = objectMapper.readValue(message, Request.class);

            request.setStatus("success");

            System.out.println(request);

            String jsonRequest = objectMapper.writeValueAsString(request);

            System.out.println(jsonRequest);

            kafkaTemplate.send("responses-topic", jsonRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
