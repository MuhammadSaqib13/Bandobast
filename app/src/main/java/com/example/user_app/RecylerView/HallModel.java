package com.example.user_app.RecylerView;

public class HallModel {

    private int image;
    private String name;
    private String Description;
    private String Amnt_Desc;

    public HallModel(int image) {
        this.image = image;

    }

    public HallModel(int image, String name, String description, String amnt_Desc) {
        this.image = image;
        this.name = name;
        Description = description;
        Amnt_Desc = amnt_Desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAmnt_Desc() {
        return Amnt_Desc;
    }

    public void setAmnt_Desc(String amnt_Desc) {
        Amnt_Desc = amnt_Desc;
    }

}
