package com.example.core_demo.service;

import com.example.core_demo.entity.Request;
import com.example.core_demo.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void saveRequest(Request request) {
        requestRepository.save(request);
    }

    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    public Request findByBankAccount(String bankAccount) {
        return requestRepository.findByBankAccount(bankAccount);
    }
}
