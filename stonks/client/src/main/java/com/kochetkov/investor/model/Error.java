package com.kochetkov.investor.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Error {
    private int status;
    private String error;
    private String message;
}
