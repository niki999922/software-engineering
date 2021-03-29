package com.kochetkov.fitnes.event;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

@Data
@MappedSuperclass
public abstract class Event<T> implements Consumer<T> {
    @Id
    private final String id = UUID.randomUUID().toString();
    private final LocalDateTime created = LocalDateTime.now();
    @NotEmpty
    private String subscriptionId;
}
