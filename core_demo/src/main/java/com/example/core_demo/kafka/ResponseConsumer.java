package com.example.core_demo.kafka;

import com.example.core_demo.entity.Request;
import com.example.core_demo.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResponseConsumer {

    private RequestService requestService;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ResponseConsumer(RequestService requestService, KafkaTemplate<String, String> kafkaTemplate) {
        this.requestService = requestService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "responses-topic", groupId = "core-group")
    public void listen(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Request request = objectMapper.readValue(message, Request.class);

            if (request != null) {
                System.out.println("Request status: " + request.getStatus());
                requestService.saveRequest(request);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
