package com.kochetkov.investor.command.user;

import com.kochetkov.investor.model.User;
import com.kochetkov.investor.util.HttpUtils;
import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthorizeCommand extends UserCommand<User> {

    public AuthorizeCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return "/" + getLogin();
    }

    @Override
    @SneakyThrows
    public User execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).GET().build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpUtils.checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(User.class).readValue(response.body());
    }

}
