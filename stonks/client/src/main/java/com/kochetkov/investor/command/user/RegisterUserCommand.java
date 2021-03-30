package com.kochetkov.investor.command.user;

import com.kochetkov.investor.model.User;
import com.kochetkov.investor.util.Resources;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.kochetkov.investor.util.HttpUtils.checkResponseCode;

public class RegisterUserCommand extends UserCommand<User> {
    public RegisterUserCommand(String login) {
        super(login);
    }

    @Override
    public String getAddress() {
        return "?login=" + getLogin();
    }

    @SneakyThrows
    @Override
    public User execute() {
        var request = HttpRequest.newBuilder().uri(getUri()).POST(HttpRequest.BodyPublishers.noBody()).build();
        var response = Resources.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        checkResponseCode(response);
        return Resources.getObjectMapper().readerFor(User.class).readValue(response.body());
    }
}
