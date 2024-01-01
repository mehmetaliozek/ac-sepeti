package com.gazi.acsepeti.models;

import java.util.Objects;

public class FoodModel {
    public int id;
    public String name;
    public String description;
    public String imageUrl;
    public double price;

    public FoodModel(int id, String name, String description, String imageUrl, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
