package com.kochetkov.fitnes.service;

import com.kochetkov.fitnes.event.Event;
import com.kochetkov.fitnes.event.subscription.SubscriptionCreated;
import com.kochetkov.fitnes.event.subscription.SubscriptionProlonged;
import com.kochetkov.fitnes.model.Subscription;
import com.kochetkov.fitnes.repository.subscription.SubscriptionCreatedRepository;
import com.kochetkov.fitnes.repository.subscription.SubscriptionProlongedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

@Service
public class SubscriptionService {
    private final SubscriptionCreatedRepository subscriptionCreatedRepository;
    private final SubscriptionProlongedRepository subscriptionProlongedRepository;

    public SubscriptionService(SubscriptionCreatedRepository subscriptionCreatedRepository,
                               SubscriptionProlongedRepository subscriptionProlongedRepository) {
        this.subscriptionCreatedRepository = subscriptionCreatedRepository;
        this.subscriptionProlongedRepository = subscriptionProlongedRepository;
    }

    public String buySubscription(int months) {
        var id = UUID.randomUUID().toString();
        var event = new SubscriptionCreated(months);
        event.setSubscriptionId(id);
        subscriptionCreatedRepository.save(event);
        return id;
    }

    public void prolongSubscription(String id, long months) {
        findById(id);
        var event = new SubscriptionProlonged(months);
        event.setSubscriptionId(id);
        subscriptionProlongedRepository.save(event);
    }

    public Subscription findById(String id) {
        var subscriptionCreatedEvents = subscriptionCreatedRepository.findSubscriptionCreatedBySubscriptionId(id);
        var subscriptionProlongedEvents = subscriptionProlongedRepository.findSubscriptionProlongedBySubscriptionId(id);
        var allEvents = new ArrayList<Event<Subscription>>() {{
            addAll(subscriptionCreatedEvents);
            addAll(subscriptionProlongedEvents);
        }};
        allEvents.sort(Comparator.comparing(Event::getCreated));
        var subscription = new Subscription();
        allEvents.forEach(e -> e.accept(subscription));
        if (subscription.getId() == null || subscription.getExpiresAt() == null) {
            throw new RuntimeException("No such subscription");
        }
        return subscription;
    }

}
