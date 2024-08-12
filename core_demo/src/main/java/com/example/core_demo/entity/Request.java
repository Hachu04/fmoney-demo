package com.example.core_demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bankAccount;

    @Column(nullable = false)
    private String securitiesAccount;

    @Column(nullable = false)
    private BigDecimal amount;
    private String status;

    public Request() {
    }

    public Request(Long id, String bankAccount, String securitiesAccount, BigDecimal amount, String status) {
        this.id = id;
        this.bankAccount = bankAccount;
        this.securitiesAccount = securitiesAccount;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getSecuritiesAccount() {
        return securitiesAccount;
    }

    public void setSecuritiesAccount(String securitiesAccount) {
        this.securitiesAccount = securitiesAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", bankAccount='" + bankAccount + '\'' +
                ", securitiesAccount='" + securitiesAccount + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
