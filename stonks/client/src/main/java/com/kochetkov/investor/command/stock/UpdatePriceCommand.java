package com.kochetkov.investor.command.stock;

import com.kochetkov.investor.model.Stock;
import com.kochetkov.investor.util.HttpUtils;
import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdatePriceCommand extends StockCommand<Stock> {
    private final Long price;

    public UpdatePriceCommand(String company, Long price) {
        super(company);
        this.price = price;
    }

    @Override
    public String getAddress() {
        return "/" + getCompany() + "?price=" + price;
    }

    @Override
    @SneakyThrows
    public Stock execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).PUT(HttpRequest.BodyPublishers.noBody()).build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(Stock.class).readValue(response.body());
    }
}
