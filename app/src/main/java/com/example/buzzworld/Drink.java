package com.example.buzzworld;

public class Drink {
    private String name;
    private int imageResourceId;
    private String description;

    public Drink(String name, int imageResourceId, String description) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.description = description;
    }

    public static final Drink[] drinks = {
            new Drink("Latte", R.drawable.latte_img, "Hot latte for your thirst"),
            new Drink("Capuccino", R.drawable.capucinno_img, "Very hot capuccino to start your day")
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return getName();
    }
}
