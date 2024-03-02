package com.example.user_app.Retrofit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseVendorType {
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
    private List<VendorTypeControls> data ;

    public ResponseVendorType(String statusCode, String message, String error, List<VendorTypeControls> data) {
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

    public List<VendorTypeControls> getData() {
        return data;
    }

    public void setData(List<VendorTypeControls> data) {
        this.data = data;
    }
}
