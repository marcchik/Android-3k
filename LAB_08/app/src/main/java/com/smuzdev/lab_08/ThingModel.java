package com.smuzdev.lab_08;

public class ThingModel {

    public String title;
    public String description;
    public  String discoveredPlace;
    public byte[] byteImage;

    public ThingModel(String title, String description, String discoveredPlace, byte[] byteImage) {
        this.title = title;
        this.description = description;
        this.discoveredPlace = discoveredPlace;
        this.byteImage = byteImage;
    }
}
