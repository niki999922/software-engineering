package com.kochetkov.investor.command.user;

import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.kochetkov.investor.util.HttpUtils.checkResponseCode;

public class GetMoneyCommand extends UserCommand<Long> {
    public GetMoneyCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return "/money/" + getLogin();
    }

    @Override
    @SneakyThrows
    public Long execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).GET().build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(Long.class).readValue(response.body());
    }
}
