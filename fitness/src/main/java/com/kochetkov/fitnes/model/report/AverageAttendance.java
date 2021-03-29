package com.kochetkov.fitnes.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class AverageAttendance {
    private Double attendanceCount;
    private Double averageAttendanceTime;
    private HashSet<String> uniqClients;
    private Double getAttendanceCountStatistic() {
        return attendanceCount / uniqClients.size();
    }
}
