package com.example.user_app.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseVendorTypeList {
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
        private List<VendorListControls> data = null;

        public ResponseVendorTypeList(String statusCode, String message, String error, List<VendorListControls> data) {
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

        public List<VendorListControls> getData() {
            return data;
        }

        public void setData(List<VendorListControls> data) {
            this.data = data;
        }


}
