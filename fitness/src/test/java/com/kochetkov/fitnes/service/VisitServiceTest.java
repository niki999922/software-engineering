package com.kochetkov.fitnes.service;

import com.kochetkov.fitnes.clock.Clock;
import com.kochetkov.fitnes.clock.FixedClock;
import com.kochetkov.fitnes.repository.visit.ClientEnteredRepository;
import com.kochetkov.fitnes.repository.visit.ClientLeftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private Clock clock;
    @Autowired
    private ClientEnteredRepository clientEnteredRepository;
    @Autowired
    private ClientLeftRepository clientLeftRepository;

    @BeforeEach
    public void init() {
        clientEnteredRepository.deleteAll();
        clientLeftRepository.deleteAll();
    }

    @Test
    public void noSubscriptionCannotEnterAndAfterSubscriptionCan() {
        var notEnter = "test";
        var expectedExceptionMessage = "No such subscription";
        try {
            visitService.enter(notEnter);
        } catch (Exception ex) {
            Assertions.assertEquals(expectedExceptionMessage, ex.getMessage());
            var validId = subscriptionService.buySubscription(1);
            assertDoesNotThrow(() -> visitService.enter(validId));
            return;
        }
        Assertions.fail();
    }

    @Test
    public void canNotEnterSubscriptionExpired() {
        var expectedExceptionMessage = "Your subscription has expired";
        var expiredId = subscriptionService.buySubscription(1);
        var subscription = subscriptionService.findById(expiredId);
        assertTrue(clock instanceof FixedClock);
        var fixedClock = ((FixedClock) clock);
        fixedClock.setNow(clock.now().plusMonths(2));
        assertTrue(subscription.isExpired(clock.now()));
        try {
            visitService.enter(expiredId);
        } catch (Exception ex) {
            Assertions.assertEquals(expectedExceptionMessage, ex.getMessage());
            return;
        }
        Assertions.fail();
    }

    @Test
    public void canLeave() {
        var subscriptionId = subscriptionService.buySubscription(1);
        visitService.enter(subscriptionId);
        assertDoesNotThrow(() -> visitService.leave(subscriptionId));
    }

    @Test
    public void canFindClientEnteredEventsAfter() {
        var first = subscriptionService.buySubscription(1);
        var second = subscriptionService.buySubscription(1);
        visitService.enter(first);
        visitService.enter(first);
        visitService.enter(second);
        assertEquals(3, visitService.findClientEnteredEventsAfter(clock.now()).size());
    }

    @Test
    public void canFindClientLeftEventsAfter() {
        var first = subscriptionService.buySubscription(1);
        var second = subscriptionService.buySubscription(1);
        visitService.enter(first);
        visitService.enter(second);
        visitService.leave(first);
        visitService.leave(second);
        assertEquals(2, visitService.findClientLeftEventsAfter(clock.now()).size());
    }
}
