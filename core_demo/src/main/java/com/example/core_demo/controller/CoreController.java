package com.example.core_demo.controller;

import com.example.core_demo.entity.Request;
import com.example.core_demo.exception.InvalidAmountException;
import com.example.core_demo.exception.MissingFieldException;
import com.example.core_demo.exception.RequestNotFoundException;
import com.example.core_demo.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/core")
public class CoreController {

    private final RequestService requestService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public CoreController(RequestService requestService, KafkaTemplate<String, String> kafkaTemplate) {
        this.requestService = requestService;
        this.kafkaTemplate = kafkaTemplate;
    }



    @PostMapping("/collect")
    public String collect(@RequestBody Request request) throws JsonProcessingException {

        if (request.getBankAccount() == null || request.getAmount() == null || request.getSecuritiesAccount() == null) {
            throw new MissingFieldException("All fields except status must be provided and not null");
        }

        if (request.getAmount().compareTo(BigDecimal.valueOf(0.0)) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        request.setStatus("pending");
        requestService.saveRequest(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        System.out.println(jsonRequest);

        kafkaTemplate.send("requests-topic", jsonRequest);
        return "Collect request received with Id: " + request.getId();
    }

    @GetMapping("/status/{id}")
    public String getStatus(@PathVariable Long id) {
        Request request = requestService.findById(id);
        if(request == null) {
            throw new RequestNotFoundException(id);
        }
        return "Request id: " + id + "\nstatus: " + request.getStatus();
    }

//    public ResponseEntity<String> collect(@RequestBody Request request) throws JsonProcessingException {
//        request.setStatus("pending");
//        requestService.saveRequest(request);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonRequest = objectMapper.writeValueAsString(request);
//
//        System.out.println(jsonRequest);
//
//        kafkaTemplate.send("requests-topic", jsonRequest);
//        return ResponseEntity.ok("Collect request received with Id: " + request.getId());
//    }
}
