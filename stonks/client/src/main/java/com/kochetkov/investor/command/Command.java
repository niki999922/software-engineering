package com.kochetkov.investor.command;

import java.net.URI;

public interface Command<T> {
    T execute();

    String getAddress();

    String getBaseUrl();

    default URI getUri() {
        return URI.create("http://localhost:8080" + getBaseUrl() + getAddress());
    }
}
