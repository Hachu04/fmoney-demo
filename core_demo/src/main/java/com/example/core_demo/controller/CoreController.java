package com.example.core_demo.controller;

import com.example.core_demo.entity.Request;
import com.example.core_demo.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/core")
public class CoreController {

    private RequestService requestService;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public CoreController(RequestService requestService, KafkaTemplate<String, String> kafkaTemplate) {
        this.requestService = requestService;
        this.kafkaTemplate = kafkaTemplate;
    }



    @PostMapping("/collect")
    public ResponseEntity<String> collect(@RequestBody Request request) throws JsonProcessingException {
        request.setStatus("pending");
        requestService.saveRequest(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        System.out.println(jsonRequest);

        kafkaTemplate.send("requests-topic", jsonRequest);
        return ResponseEntity.ok("Collect request received with Id: " + request.getId());
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        Request request = requestService.findById(id);
        if(request == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok("Request id: " + id + " status: " + request.getStatus());
    }
}
