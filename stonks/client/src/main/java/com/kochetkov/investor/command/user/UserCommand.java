package com.kochetkov.investor.command.user;

import com.kochetkov.investor.command.Command;
import lombok.Data;

@Data
public abstract class UserCommand<T> implements Command<T> {
    private final String login;
    public String getBaseUrl() {
        return "/api/v1/user";
    }
}
