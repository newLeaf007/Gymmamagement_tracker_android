package com.example.gymmamagement.modals;

import com.google.gson.annotations.SerializedName;

public class ResponseToken {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResponseToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
