package com.example.core_demo.repository;

import com.example.core_demo.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    public Request findByBankAccount(String bankAccount);
}
