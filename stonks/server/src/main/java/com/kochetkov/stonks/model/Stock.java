package com.kochetkov.stonks.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    @NotEmpty
    private String company;

    @Range(min = 1L, max = 1000L)
    private long price;

    @Range(max = 500L)
    private long count;
}
