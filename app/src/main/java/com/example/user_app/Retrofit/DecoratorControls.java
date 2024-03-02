package com.example.user_app.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DecoratorControls {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("setupRequirementId")
    @Expose
    private String setupRequirementId;

    @SerializedName("eventId")
    @Expose
    private String eventId;

    @SerializedName("personRange")
    @Expose
    private String personRange;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("location")
    @Expose
    private String location;


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
    private String image;

    @SerializedName("active")
    @Expose
    private String active;


    public DecoratorControls(String code, String shopName, String price, String location, String image) {
        this.code = code;
        this.shopName = shopName;
        this.price = price;
        this.location = location;
        this.image = image;
    }

    public DecoratorControls(String id, String code, String shopName, String setupRequirementId, String eventId, String personRange, String price, String location, String lat, String aLong, String description, String image, String active) {
        this.id = id;
        this.code = code;
        this.shopName = shopName;
        this.setupRequirementId = setupRequirementId;
        this.eventId = eventId;
        this.personRange = personRange;
        this.price = price;
        this.location = location;
        this.lat = lat;
        Long = aLong;
        this.description = description;
        this.image = image;
        this.active = active;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSetupRequirementId() {
        return setupRequirementId;
    }

    public void setSetupRequirementId(String setupRequirementId) {
        this.setupRequirementId = setupRequirementId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
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
