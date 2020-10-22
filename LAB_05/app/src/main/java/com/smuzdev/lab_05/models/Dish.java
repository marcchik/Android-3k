package com.smuzdev.lab_05.models;

public class Dish {

    private String dishName;
    private String dishCategory;
    private String dishDescription;
    private String dishIngredients;
    private String dishRecipe;
    private int dishImage;
    private String dishCookingTime;

    public Dish(String dishName, String dishCategory, String dishDescription, String dishIngredients, String dishRecipe, int dishImage, String dishCookingTime) {
        this.dishName = dishName;
        this.dishCategory = dishCategory;
        this.dishDescription = dishDescription;
        this.dishIngredients = dishIngredients;
        this.dishRecipe = dishRecipe;
        this.dishImage = dishImage;
        this.dishCookingTime = dishCookingTime;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishCategory() {
        return dishCategory;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public String getDishIngredients() {
        return dishIngredients;
    }

    public String getDishRecipe() {
        return dishRecipe;
    }

    public int getDishImage() {
        return dishImage;
    }

    public String getDishCookingTime() {
        return dishCookingTime;
    }
}
