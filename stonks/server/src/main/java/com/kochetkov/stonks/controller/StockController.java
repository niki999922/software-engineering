package com.kochetkov.stonks.controller;

import com.kochetkov.stonks.model.Stock;
import com.kochetkov.stonks.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stock> list() {
        return stockService.list();
    }

    @PostMapping
    public Stock add(@RequestBody Stock stock) {
        return stockService.add(stock);
    }

    @PutMapping("/{company}")
    public Stock updatePrice(@PathVariable String company, @RequestParam Long price) {
        return stockService.updatePrice(company, price);
    }
}
