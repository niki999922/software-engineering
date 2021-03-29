package com.kochetkov.fitnes.repository.subscription;

import com.kochetkov.fitnes.event.subscription.SubscriptionCreated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionCreatedRepository extends JpaRepository<SubscriptionCreated, String> {
    List<SubscriptionCreated> findSubscriptionCreatedBySubscriptionId(String subscriptionId);
}
