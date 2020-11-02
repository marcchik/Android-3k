package com.smuzdev.lab_05.models;

public class Dish {

    private String dishName;
    private String dishCategory;
    private String dishDescription;
    private String dishIngredients;
    private String dishRecipe;
    private String dishImage;
    private String dishCookingTime;

    public Dish(String dishName, /*String dishCategory,*/ String dishDescription, /*String dishIngredients, String dishRecipe,*/String dishCookingTime, String dishImage ) {
        this.dishName = dishName;
//        this.dishCategory = dishCategory;
        this.dishDescription = dishDescription;
//        this.dishIngredients = dishIngredients;
//        this.dishRecipe = dishRecipe;
        this.dishCookingTime = dishCookingTime;
        this.dishImage = dishImage;
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

    public String getDishImage() {
        return dishImage;
    }

    public String getDishCookingTime() {
        return dishCookingTime;
    }
}
