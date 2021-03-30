package com.kochetkov.investor.command.user;

import com.kochetkov.investor.model.Stock;
import com.kochetkov.investor.util.HttpUtils;
import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class GetPortfolioCommand extends UserCommand<List<Stock>> {
    public GetPortfolioCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return "/portfolio/" + getLogin();
    }

    @Override
    @SneakyThrows
    public List<Stock> execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).GET().build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Arrays.asList(Resources.getObjectMapper().readerFor(Stock[].class).readValue(response.body()));
    }
}
