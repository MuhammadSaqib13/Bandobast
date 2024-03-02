package com.example.user_app.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCaterer {
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("data")
    @Expose
    private List<CatererControls> data = null;

    public ResponseCaterer(String statusCode, String message, String error, List<CatererControls> data) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<CatererControls> getData() {
        return data;
    }

    public void setData(List<CatererControls> data) {
        this.data = data;
    }
}
