package com.kochetkov.fitnes.repository.visit;

import com.kochetkov.fitnes.event.visit.ClientLeft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientLeftRepository extends JpaRepository<ClientLeft, String> {
    List<ClientLeft> findClientLeftByCreatedAfterOrderByCreated(LocalDateTime date);
}
