package com.kochetkov.investor.command.stock;

import com.kochetkov.investor.model.Stock;
import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.kochetkov.investor.util.HttpUtils.checkResponseCode;

public class AddStockCommand extends StockCommand<Stock> {
    private final Long price;
    private final Long count;

    public AddStockCommand(String company, Long price, Long count) {
        super(company);
        this.price = price;
        this.count = count;
    }

    @Override
    public String getAddress() {
        return "";
    }

    @Override
    @SneakyThrows
    public Stock execute() {
        var stock = new Stock(getCompany(), price, count);
        var request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(Resources.getObjectMapper().writeValueAsString(stock)))
                .build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(Stock.class).readValue(response.body());
    }
}
