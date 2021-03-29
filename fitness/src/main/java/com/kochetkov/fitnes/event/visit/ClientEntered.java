package com.kochetkov.fitnes.event.visit;

import com.kochetkov.fitnes.event.Event;
import com.kochetkov.fitnes.model.Visit;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ClientEntered extends Event<Visit> {

    @NotNull
    private LocalDateTime enteredAt;

    @Override
    public void accept(Visit visit) {
        if (visit.getEnteredAt() != null) {
            throw new RuntimeException("Client in the club");
        }
        visit.setSubscriptionId(getSubscriptionId());
        visit.setEnteredAt(getCreated());
    }
}
