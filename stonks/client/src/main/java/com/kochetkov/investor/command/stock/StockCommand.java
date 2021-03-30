package com.kochetkov.investor.command.stock;

import com.kochetkov.investor.command.Command;
import lombok.Data;

@Data
public abstract class StockCommand<T> implements Command<T> {
    private final String company;
    public String getBaseUrl() {
        return "/api/v1/stock";
    }
}
