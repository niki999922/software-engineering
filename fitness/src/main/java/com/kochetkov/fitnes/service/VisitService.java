package com.kochetkov.fitnes.service;

import com.kochetkov.fitnes.clock.Clock;
import com.kochetkov.fitnes.event.visit.ClientEntered;
import com.kochetkov.fitnes.event.visit.ClientLeft;
import com.kochetkov.fitnes.repository.visit.ClientEnteredRepository;
import com.kochetkov.fitnes.repository.visit.ClientLeftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitService {
    private final SubscriptionService subscriptionService;
    private final ReportService reportService;
    private final ClientEnteredRepository clientEnteredRepository;
    private final ClientLeftRepository clientLeftRepository;
    private final Clock clock;

    public VisitService(SubscriptionService subscriptionService,
                        ReportService reportService,
                        ClientEnteredRepository clientEnteredRepository,
                        ClientLeftRepository clientLeftRepository,
                        Clock clock) {
        this.subscriptionService = subscriptionService;
        this.reportService = reportService;
        this.clientEnteredRepository = clientEnteredRepository;
        this.clientLeftRepository = clientLeftRepository;
        this.clock = clock;
    }

    public void enter(String id) {
        var now = clock.now();
        var subscription = subscriptionService.findById(id);
        subscription.validate(now);
        var event = new ClientEntered(now);
        event.setSubscriptionId(id);
        reportService.updateDailyStatistics(now);
        clientEnteredRepository.save(event);
    }

    public void leave(String id) {
        var now = clock.now();
        var event = new ClientLeft(now);
        event.setSubscriptionId(id);
        reportService.updateAverageAttendance(now, id);
        clientLeftRepository.save(event);
    }

    public List<ClientEntered> findClientEnteredEventsAfter(LocalDateTime dateTime) {
        return clientEnteredRepository.findClientEnteredByCreatedAfterOrderByCreated(dateTime);
    }

    public List<ClientLeft> findClientLeftEventsAfter(LocalDateTime dateTime) {
        return clientLeftRepository.findClientLeftByCreatedAfterOrderByCreated(dateTime);
    }

}
