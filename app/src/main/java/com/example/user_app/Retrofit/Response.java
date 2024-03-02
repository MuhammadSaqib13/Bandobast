package com.example.user_app.Retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Response {

    @Expose
    @SerializedName("statusCode")
    private String statusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("data")
    private JsonElement data;

    /**
     * No args constructor for use in serialization
     *
     */
    public Response() {
    }

    public Response(String statusCode, String message, String error, JsonElement data) {
        super();
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

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public JsonElement  getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

}