package com.gazi.acsepeti.models;

import java.util.ArrayList;

public class RestaurantModel {
    public int id;
    public String name;
    public String imageUrl;
    public ArrayList<FoodModel> foods;

    public RestaurantModel(int id, String name, String imgUrl, ArrayList<FoodModel> foods) {
        this.id = id;
        this.name = name;
        this.imageUrl = imgUrl;
        this.foods = foods;
    }
}
