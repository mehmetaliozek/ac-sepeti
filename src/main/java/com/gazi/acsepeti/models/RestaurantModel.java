package com.gazi.acsepeti.models;

import java.util.ArrayList;

/**
 * @param id restoranýn idsinin tutuldu deðiþken
 * @param name restoranýn adýnýn tutuldu deðiþken
 * @param imageUrl restoranýn resminin tutuldu deðiþken
 * @param foods restoranýn satýþ yaptýðý yemeklerin tutuldu deðiþken
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
