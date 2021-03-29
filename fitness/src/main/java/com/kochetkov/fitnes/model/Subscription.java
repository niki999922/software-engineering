package com.kochetkov.fitnes.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Subscription {
    private String id;
    private LocalDateTime expiresAt;

    public boolean isExpired(LocalDateTime dateTime) {
        return expiresAt == null || expiresAt.isBefore(dateTime);
    }

    public void validate(LocalDateTime expirationTime) {
        if (expiresAt == null) {
            throw new RuntimeException("No subscription");
        }
        if (expiresAt.isBefore(expirationTime)) {
            throw new RuntimeException("Your subscription has expired");
        }
    }
}
