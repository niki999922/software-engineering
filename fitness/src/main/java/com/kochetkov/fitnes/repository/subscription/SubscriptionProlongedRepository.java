package com.kochetkov.fitnes.repository.subscription;

import com.kochetkov.fitnes.event.subscription.SubscriptionProlonged;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionProlongedRepository extends JpaRepository<SubscriptionProlonged, String> {
    List<SubscriptionProlonged> findSubscriptionProlongedBySubscriptionId(String subscriptionId);
}
