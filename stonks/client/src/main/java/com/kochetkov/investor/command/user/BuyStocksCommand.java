package com.kochetkov.investor.command.user;

import com.kochetkov.investor.util.HttpUtils;
import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BuyStocksCommand extends UserCommand<Boolean> {
    private final String company;
    private final Long count;

    public BuyStocksCommand(String login, String company, Long count) {
        super(login);
        this.company = company;
        this.count = count;
    }

    @Override
    public String getAddress() {
        return "/buy/" + getLogin() + "?company=" + company + "&count=" + count;
    }

    @Override
    @SneakyThrows
    public Boolean execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).PUT(HttpRequest.BodyPublishers.noBody()).build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return true;
    }
}
