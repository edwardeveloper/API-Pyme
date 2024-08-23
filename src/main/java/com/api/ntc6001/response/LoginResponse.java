package com.api.ntc6001.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }
}
