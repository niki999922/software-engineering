package com.kochetkov.fitnes.repository.visit;

import com.kochetkov.fitnes.event.visit.ClientEntered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientEnteredRepository extends JpaRepository<ClientEntered, String> {
    List<ClientEntered> findClientEnteredByCreatedAfterOrderByCreated(LocalDateTime date);

    List<ClientEntered> findAllBySubscriptionId(String subscriptionId);
}
