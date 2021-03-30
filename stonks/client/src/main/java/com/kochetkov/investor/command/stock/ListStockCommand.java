package com.kochetkov.investor.command.stock;

import com.kochetkov.investor.model.Stock;
import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static com.kochetkov.investor.util.HttpUtils.checkResponseCode;

public class ListStockCommand extends StockCommand<List<Stock>> {
    public ListStockCommand(String company) {
        super(company);
    }

    @Override
    public String getAddress() {
        return "";
    }

    @Override
    @SneakyThrows
    public List<Stock> execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).GET().build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        checkResponseCode(response);
        return Arrays.asList(Resources.getObjectMapper().readerFor(Stock[].class).readValue(response.body()));
    }
}
