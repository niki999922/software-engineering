package com.kochetkov.fitnes.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Visit {
    private String subscriptionId;
    private LocalDateTime enteredAt;
    private LocalDateTime leftAt;
}
