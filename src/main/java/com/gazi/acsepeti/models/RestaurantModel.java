package com.gazi.acsepeti.models;

import java.util.ArrayList;

/**
 * @param id restoran�n idsinin tutuldu de�i�ken
 * @param name restoran�n ad�n�n tutuldu de�i�ken
 * @param imageUrl restoran�n resminin tutuldu de�i�ken
 * @param foods restoran�n sat�� yapt��� yemeklerin tutuldu de�i�ken
 */
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
