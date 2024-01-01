package com.gazi.acsepeti.models;

public class CartItemModel {
    public FoodModel food;
    public int count;

    public CartItemModel(FoodModel food) {
        this.food = food;
        this.count = 1;
    }
}
