package com.cristeabianca.userms.jwt;

import java.util.List;

public class LoginResponse {
    private String username;
    private String token;
    private List<String> roles;

    public LoginResponse(String username, String token, List<String> roles) {
        this.username = username;
        this.token = token;
        this.roles = roles;
    }


    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public List<String> getRoles() {
        return roles;
    }
}
