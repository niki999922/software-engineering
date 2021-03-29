package com.kochetkov.fitnes.model.report;

import lombok.Data;

import java.util.Map;

@Data
public class DailyStatistics {
    private final Map<String, Long> attendance;
}
