package com.kochetkov.stonks.service;

import com.kochetkov.stonks.model.Stock;
import com.kochetkov.stonks.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock add(Stock stock) {
        return stockRepository.save(stock);
    }

    public List<Stock> list() {
        return stockRepository.findAll();
    }

    public Stock updatePrice(String company, Long price) {
        var stock = stockRepository.findByCompany(company).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such stock"));
        stock.setPrice(price);
        return stockRepository.save(stock);
    }
}
