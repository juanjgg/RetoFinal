package com.curso.retofinal.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cashouts")
public class Cashout {
    @Id
    private String id;
    private Long userId;
    private Double amount;

    public Cashout(Long userId, Double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Cashout() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
