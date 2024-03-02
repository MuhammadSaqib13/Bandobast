package com.example.user_app.Retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorTypeControls {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("transportationName")
    @Expose
    private String transportationName;

    @SerializedName("setupRequirement")
    @Expose
    private String setupRequirement;

    @SerializedName("event")
    @Expose
    private String event;


    @SerializedName("personRange")
    @Expose
    private String personRange;


    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("serviceType")
    @Expose
    private String serviceType;

    @SerializedName("vehicleType")
    @Expose
    private String vehicleType;

    @SerializedName("location")
    @Expose
    private String location;


    @SerializedName("shift")
    @Expose
    private String shift;

    @SerializedName("menu")
    @Expose
    private String menu;

    @SerializedName("menuCategory")
    @Expose
    private String menuCategory;

    @SerializedName("menuSubCategory")
    @Expose
    private String menuSubCategory;

    @SerializedName("priceCategory")
    @Expose
    private String priceCategory;

    @SerializedName("deliveryCharges")
    @Expose
    private String deliveryCharges;

    @SerializedName("distance")
    @Expose
    private double distance;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("levelCategory")
    @Expose
    private String levelCategory;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("active")
    @Expose
    private String active;

    public VendorTypeControls(String image) {
        this.image = image;
    }

    public VendorTypeControls(String code,String name, String price, String location, String image) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.location = location;
        this.image = image;
    }
    public VendorTypeControls(String name, String price, String location, String image) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.image = image;
    }

    public VendorTypeControls(String image, String name, String location) {
        this.image = image;
        this.name = name;
        this.location = location;
    }

    public VendorTypeControls(String image, String _code, String Name, double distance, String price) {
        this.image =image;
        this.code = _code;
        this.name = Name;
        this.distance = distance;
        this.price = price;

    }


    public String getLevelCategory() {
        return levelCategory;
    }

    public void setLevelCategory(String levelCategory) {
        this.levelCategory = levelCategory;
    }

    public String getTransportationName() {
        return transportationName;
    }


    public void setTransportationName(String transportationName) {
        this.transportationName = transportationName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetupRequirement() {
        return setupRequirement;
    }

    public void setSetupRequirement(String setupRequirement) {
        this.setupRequirement = setupRequirement;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPersonRange() {
        return personRange;
    }

    public void setPersonRange(String personRange) {
        this.personRange = personRange;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }

    public String getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(String menuSubCategory) {
        this.menuSubCategory = menuSubCategory;
    }

    public String getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(String priceCategory) {
        this.priceCategory = priceCategory;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
