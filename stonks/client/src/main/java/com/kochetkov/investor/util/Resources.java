package com.kochetkov.investor.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class Resources {
    private static final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }
}
