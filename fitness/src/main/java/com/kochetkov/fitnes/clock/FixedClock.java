package com.kochetkov.fitnes.clock;

import java.time.LocalDateTime;

public class FixedClock implements Clock {

    private LocalDateTime now = LocalDateTime.now();

    @Override
    public LocalDateTime now() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }
}
