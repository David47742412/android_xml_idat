package com.tuttobello.front.model.auth;

public class SAuth {

    String email;
    String password;
    public SAuth(String emailOrUsername, String password) {
        this.email = emailOrUsername;
        this.password = password;
    }
}
