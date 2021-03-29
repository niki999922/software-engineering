package com.kochetkov.fitnes.event.subscription;

import com.kochetkov.fitnes.event.Event;
import com.kochetkov.fitnes.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SubscriptionCreated extends Event<Subscription> {

    @Range(min = 1, max = 12)
    private long months;

    @Override
    public void accept(Subscription subscription) {
        subscription.setId(getSubscriptionId());
        subscription.setExpiresAt(getCreated().plusMonths(months));
    }
}
