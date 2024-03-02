package com.example.user_app.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatererControls {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private final String code;
    @SerializedName("restaurant")
    @Expose
    private final String restaurant;
    @SerializedName("menuId")
    @Expose
    private String menuId;

    @SerializedName("menuCategoryId")
    @Expose
    private String menuCategoryId;

    @SerializedName("menuSubCategoryId")
    @Expose
    private String menuSubCategoryId;

    @SerializedName("price")
    @Expose
    private final String price;


    @SerializedName("priceCategoryId")
    @Expose
    private String priceCategoryId;

    @SerializedName("eventId")
    @Expose
    private String eventId;

    @SerializedName("deliveryCharges")
    @Expose
    private String deliveryCharges;


    @SerializedName("location")
    @Expose
    private final String location;


    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("long")
    @Expose
    private String Long;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private final String image;

    @SerializedName("active")
    @Expose
    private String active;

    public CatererControls(String code, String restaurant, String price, String location, String image) {
        this.code = code;
        this.restaurant = restaurant;
        this.price = price;
        this.location = location;
        this.image = image;
    }

    public CatererControls(String id, String code, String restaurant, String menuId, String menuCategoryId, String menuSubCategoryId, String price, String priceCategoryId, String eventId, String deliveryCharges, String location, String lat, String aLong, String description, String image, String active) {
        this.id = id;
        this.code = code;
        this.restaurant = restaurant;
        this.menuId = menuId;
        this.menuCategoryId = menuCategoryId;
        this.menuSubCategoryId = menuSubCategoryId;
        this.price = price;
        this.priceCategoryId = priceCategoryId;
        this.eventId = eventId;
        this.deliveryCharges = deliveryCharges;
        this.location = location;
        this.lat = lat;
        Long = aLong;
        this.description = description;
        this.image = image;
        this.active = active;
    }
}
