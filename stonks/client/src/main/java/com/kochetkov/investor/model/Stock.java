package com.kochetkov.investor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private String company;
    private long price;
    private long count;
}
