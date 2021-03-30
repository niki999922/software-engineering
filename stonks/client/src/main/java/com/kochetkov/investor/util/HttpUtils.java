package com.kochetkov.investor.util;

import com.kochetkov.investor.model.Error;
import lombok.SneakyThrows;

import java.net.http.HttpResponse;

public class HttpUtils {
    @SneakyThrows
    public static void checkResponseCode(HttpResponse<String> response) {
        if (response.statusCode() == 200) return;
        throw new RuntimeException(Resources.getObjectMapper().readValue(response.body(), Error.class).getMessage());
    }
}
