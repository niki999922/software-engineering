package com.kochetkov.fitnes.service;

import com.kochetkov.fitnes.clock.Clock;
import com.kochetkov.fitnes.clock.FixedClock;
import com.kochetkov.fitnes.repository.subscription.SubscriptionCreatedRepository;
import com.kochetkov.fitnes.repository.subscription.SubscriptionProlongedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private Clock clock;
    @Autowired
    private SubscriptionCreatedRepository subscriptionCreatedRepository;
    @Autowired
    private SubscriptionProlongedRepository subscriptionProlongedRepository;

    @BeforeEach
    public void init() {
        subscriptionCreatedRepository.deleteAll();
        subscriptionProlongedRepository.deleteAll();
        assertTrue(clock instanceof FixedClock);
        var fixedClock = ((FixedClock) clock);
        fixedClock.setNow(LocalDateTime.now());
    }

    @Test
    public void canBuySubscriptionAndNotProlongOnInvalid() {
        var id = subscriptionService.buySubscription(1);
        Assertions.assertDoesNotThrow(() -> subscriptionService.buySubscription(1));
        assertThrows(TransactionSystemException.class, () -> subscriptionService.prolongSubscription(id, 0));
        assertThrows(TransactionSystemException.class, () -> subscriptionService.prolongSubscription(id, -1));
        assertThrows(TransactionSystemException.class, () -> subscriptionService.prolongSubscription(id, 13));
    }

    @Test
    public void findCorrectSubscription() {
        var id = subscriptionService.buySubscription(2);
        var subscription = subscriptionService.findById(id);

        assertEquals(clock.now().plusMonths(2).withNano(0), subscription.getExpiresAt().withNano(0));
    }

    @Test
    public void canProlongSubscriptionNotExpired() {
        var id = subscriptionService.buySubscription(2);
        subscriptionService.prolongSubscription(id, 1);
        var subscription = subscriptionService.findById(id);
        assertEquals(clock.now().plusMonths(3).withNano(0), subscription.getExpiresAt().withNano(0));
    }

    @Test
    public void canProlongSubscriptionExpired() {
        var id = subscriptionService.buySubscription(1);
        var subscription = subscriptionService.findById(id);
        var fixedClock = ((FixedClock) clock);
        fixedClock.setNow(clock.now().plusMonths(2));
        assertTrue(subscription.isExpired(clock.now()));
        subscriptionService.prolongSubscription(id, 1);
        subscription = subscriptionService.findById(id);
        assertEquals(clock.now().withNano(0), subscription.getExpiresAt().withNano(0));
    }


    @Test
    public void canNotProlongSubscriptionNonExistentSubscription() {
        var nonExistentId = "non-existent-id";
        var expectedExceptionMessage = "No such subscription";
        try {
            subscriptionService.prolongSubscription(nonExistentId, 1);
        } catch (Exception ex) {
            Assertions.assertEquals(expectedExceptionMessage, ex.getMessage());
            return;
        }
        Assertions.fail();
    }
}
