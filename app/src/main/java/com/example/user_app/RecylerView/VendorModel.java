package com.example.user_app.RecylerView;

public class VendorModel {

    private String name;
    private String Description, starRating;
    private int image;

    public VendorModel() {
    }

    public VendorModel(String name, String description, int image) {
        this.name = name;
        Description = description;
        this.image = image;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
