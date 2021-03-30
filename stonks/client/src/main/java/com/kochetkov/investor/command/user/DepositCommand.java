package com.kochetkov.investor.command.user;

import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.kochetkov.investor.util.HttpUtils.checkResponseCode;

public class DepositCommand extends UserCommand<Boolean> {
    private final Long money;

    public DepositCommand(String login, Long money) {
        super(login);
        this.money = money;
    }

    @Override
    public String getAddress() {
        return "/deposit/" + getLogin() + "?value=" + money;
    }

    @Override
    @SneakyThrows
    public Boolean execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).PUT(HttpRequest.BodyPublishers.noBody()).build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        checkResponseCode(response);
        return true;
    }
}
