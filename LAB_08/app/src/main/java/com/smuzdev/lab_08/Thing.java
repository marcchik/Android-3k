package com.smuzdev.lab_08;

import java.util.ArrayList;

public class Thing {

    public ArrayList<String>
            thing_id,
            thing_title,
            thing_description,
            thing_discoveredPlace;
    public ArrayList<byte[]>
            thing_image;

    public Thing(ArrayList<String> thing_id,
                 ArrayList<String> thing_title,
                 ArrayList<String> thing_description,
                 ArrayList<String> thing_discoveredPlace,
                 ArrayList<byte[]> thing_image) {
        this.thing_id = thing_id;
        this.thing_title = thing_title;
        this.thing_description = thing_description;
        this.thing_discoveredPlace = thing_discoveredPlace;
        this.thing_image = thing_image;
    }

    public ArrayList<String> getThing_id() {
        return thing_id;
    }

    public ArrayList<String> getThing_title() {
        return thing_title;
    }

    public ArrayList<String> getThing_description() {
        return thing_description;
    }

    public ArrayList<String> getThing_discoveredPlace() {
        return thing_discoveredPlace;
    }

    public ArrayList<byte[]> getThing_image() {
        return thing_image;
    }
}
