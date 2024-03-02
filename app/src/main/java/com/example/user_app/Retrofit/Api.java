package com.example.user_app.Retrofit;

import android.webkit.JsPromptResult;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("Transporter/GetTransporterNearestData")
    //@GET("Transporter/GetTransporter")
    Call<ResponseVendorType> getVendorType(@Header("Lat") double Lat, @Header("Long") double Long);

//    Call<ResponseVendorType> getVendorType(@Query("Lat") double Lat,
//                                           @Query("Long") double Long);


    @GET("Decorator/GetDecoratorNearestData")
    Call<ResponseVendorType> getDecoratorType(@Header("Lat") double Lat, @Header("Long") double Long);

    @GET("EventPlacer/GetEventPlacerNearestData")
    Call<ResponseVendorType> getEventPlacerType(@Header("Lat") double Lat, @Header("Long") double Long);

    @GET("Caterer/GetCatererNearestData")
    Call<ResponseVendorType> getCatererType(@Header("Lat") double Lat, @Header("Long") double Long);

    @GET("Photographer/GetPhotographerNearestData")
    Call<ResponseVendorType> getPhotographerType(@Header("Lat") double Lat, @Header("Long") double Long);
//    @GET("Transporter/GetTransporter")
//    Call<ResponseVendorType> getVendorType();

    @GET("Decorator/GetDecorator")
    Call<ResponseDecorator> getDecorators();

    @GET("ListOfViewService/GetVendorTypeListOfView")
    Call<ResponseVendorTypeList> getVendorList();

    @GET("VendorListOfViewService/GetEventListOfView")
    Call<ResponseVendorTypeList> getEventList();


    //Packages
    @POST("PackageGeneratorService/GetPackagesBaseOnRequirment")
    Call<Response> GetPackageResponse(@Body JsonObject params);



}
