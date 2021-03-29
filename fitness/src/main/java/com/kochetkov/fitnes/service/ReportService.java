package com.kochetkov.fitnes.service;

import com.kochetkov.fitnes.event.Event;
import com.kochetkov.fitnes.event.visit.ClientEntered;
import com.kochetkov.fitnes.event.visit.ClientLeft;
import com.kochetkov.fitnes.model.Visit;
import com.kochetkov.fitnes.model.report.AverageAttendance;
import com.kochetkov.fitnes.model.report.DailyStatistics;
import com.kochetkov.fitnes.repository.visit.ClientEnteredRepository;
import com.kochetkov.fitnes.repository.visit.ClientLeftRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportService {
    private final ClientEnteredRepository clientEnteredRepository;
    private final ClientLeftRepository clientLeftRepository;

    private AverageAttendance averageAttendance;
    private DailyStatistics dailyStatistics;

    public ReportService(ClientEnteredRepository clientEnteredRepository,
                         ClientLeftRepository clientLeftRepository) {
        this.clientEnteredRepository = clientEnteredRepository;
        this.clientLeftRepository = clientLeftRepository;
        this.averageAttendance = initAverageAttendance();
        initDailyStatistics();
    }

    /**
     * Example - 2021-03-29
     *
     * @param dateTime current date and time
     * @return current year-month-day
     */
    public static String storedDay(LocalDateTime dateTime) {
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIDNIGHT).format(DateTimeFormatter.ISO_DATE);
    }

    public DailyStatistics getDailyStatistics() {
        return dailyStatistics;
    }

    private AverageAttendance initAverageAttendance() {
        var enterEvents = clientEnteredRepository.findAll();
        var leaveEvents = clientLeftRepository.findAll();
        var allEvents = new ArrayList<Event<Visit>>() {{
            addAll(enterEvents);
            addAll(leaveEvents);
        }};

        var eventsBySubscriptionId = new HashMap<String, List<Event<Visit>>>();
        for (var event : allEvents) {
            var subscriptionId = event.getSubscriptionId();
            eventsBySubscriptionId.putIfAbsent(subscriptionId, new ArrayList<>());
            eventsBySubscriptionId.get(subscriptionId).add(event);
        }

        var amountEnter = 0;
        var durations = new ArrayList<Long>();
        for (var entry : eventsBySubscriptionId.entrySet()) {
            var events = entry.getValue();
            var timeStack = new ArrayDeque<LocalDateTime>();
            for (var event : events) {
                if (event instanceof ClientEntered) {
                    timeStack.add(((ClientEntered) event).getEnteredAt());
                    amountEnter++;
                } else if (event instanceof ClientLeft) {
                    var enter = timeStack.pop();
                    var exit = ((ClientLeft) event).getLeftAt();
                    durations.add(Duration.between(enter, exit).getSeconds());
                }
            }
        }
        var averageDurationOptional = durations.stream().mapToDouble(a -> a).average();
        var averageDuration = averageDurationOptional.isPresent() ? averageDurationOptional.getAsDouble() : 0.0;
        return new AverageAttendance(((double) amountEnter) , averageDuration, new HashSet<>(eventsBySubscriptionId.keySet()));
    }

    private void initDailyStatistics() {
        var enterEvents = clientEnteredRepository.findAll();
        var result = new HashMap<String, Long>();
        dailyStatistics = new DailyStatistics(result);
        enterEvents.forEach(event -> updateDailyStatistics(event.getEnteredAt()));
    }

    protected void updateDailyStatistics(LocalDateTime dateTime) {
        var formattedDate = storedDay(dateTime);
        dailyStatistics.getAttendance().compute(formattedDate, (key, value) -> value == null ? 1L : value + 1);
    }

    protected void updateAverageAttendance(LocalDateTime dateTime, String id) {
        var enterEventsById = clientEnteredRepository.findAllBySubscriptionId(id);
        var enterTime = enterEventsById.get(enterEventsById.size() - 1).getEnteredAt();
        var duration = Duration.between(enterTime, dateTime).getSeconds();
        var newAttendancesCount = averageAttendance.getAttendanceCount() + 1;
        averageAttendance.setAttendanceCount(newAttendancesCount);
        var oldAverage = averageAttendance.getAverageAttendanceTime();
        var newAverage = ((newAttendancesCount - 1) * oldAverage + duration) / newAttendancesCount;
        averageAttendance.setAverageAttendanceTime(newAverage);
        averageAttendance.getUniqClients().add(id);
    }

    protected void reset() {
        averageAttendance = initAverageAttendance();
        initDailyStatistics();
    }

}
