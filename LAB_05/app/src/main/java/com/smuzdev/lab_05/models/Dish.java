package com.smuzdev.lab_05.models;

public class Dish {

    private String dishName;
    private String dishDescription;
    private String dishImage;
    private String dishCookingTime;
    private  String key;

    public Dish(String dishName, String dishDescription, String dishCookingTime, String dishImage ) {
        this.dishName = dishName;
        this.dishDescription = dishDescription;
        this.dishCookingTime = dishCookingTime;
        this.dishImage = dishImage;
    }

    public Dish() {

    }

    public String getDishName() {
        return dishName;
    }


    public String getDishDescription() {
        return dishDescription;
    }

    public String getDishImage() {
        return dishImage;
    }

    public String getDishCookingTime() {
        return dishCookingTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
