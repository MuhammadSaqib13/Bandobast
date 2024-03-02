package com.example.user_app.Retrofit;

public class ApiBaseUrl {
     private String BaseUrl = "https://bandobast.syslinknetwork.com:444/";
//    private String BaseUrl = "https://5079-119-157-252-77.ngrok-free.app";

    public ApiBaseUrl() {
    }

    public String getBaseUrl() {
        return BaseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        BaseUrl = baseUrl;
    }
}

