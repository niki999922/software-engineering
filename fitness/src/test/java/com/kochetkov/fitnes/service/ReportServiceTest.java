package com.kochetkov.fitnes.service;

import com.kochetkov.fitnes.clock.Clock;
import com.kochetkov.fitnes.clock.FixedClock;
import com.kochetkov.fitnes.repository.subscription.SubscriptionCreatedRepository;
import com.kochetkov.fitnes.repository.subscription.SubscriptionProlongedRepository;
import com.kochetkov.fitnes.repository.visit.ClientEnteredRepository;
import com.kochetkov.fitnes.repository.visit.ClientLeftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.kochetkov.fitnes.service.ReportService.storedDay;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private VisitService visitService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private Clock clock;
    @Autowired
    private ClientEnteredRepository clientEnteredRepository;
    @Autowired
    private ClientLeftRepository clientLeftRepository;
    @Autowired
    private SubscriptionCreatedRepository subscriptionCreatedRepository;
    @Autowired
    private SubscriptionProlongedRepository subscriptionProlongedRepository;

    @BeforeEach
    public void init() {
        clientEnteredRepository.deleteAll();
        clientLeftRepository.deleteAll();
        subscriptionCreatedRepository.deleteAll();
        subscriptionProlongedRepository.deleteAll();
        reportService.reset();
        assertTrue(clock instanceof FixedClock);
        var fixedClock = ((FixedClock) clock);
        fixedClock.setNow(LocalDateTime.now().withNano(0));
    }

    @Test
    public void correctProcessStatistic() {
        var first = subscriptionService.buySubscription(1);
        visitService.enter(first);
        visitService.enter(first);
        var dailyStatistics = reportService.getDailyStatistics();
        var attendance = dailyStatistics.getAttendance();
        Assertions.assertEquals(1, attendance.size());
        Assertions.assertEquals(2, attendance.get(storedDay(clock.now())));
        ((FixedClock) clock).setNow(clock.now().plusDays(1));

        var second = subscriptionService.buySubscription(1);
        visitService.enter(first);
        visitService.enter(second);
        dailyStatistics = reportService.getDailyStatistics();
        attendance = dailyStatistics.getAttendance();
        Assertions.assertEquals(2, attendance.size());
        Assertions.assertEquals(2, attendance.get(storedDay(clock.now())));
    }

}
