package com.gazi.acsepeti.models;

/**
 * @param food  yemek bilgilerinin tutulduðu deðiþken
 * @param count yemek miktarinin tutulduðu deðiþken
 */
public class CartItemModel {
    public FoodModel food;
    public int count = 1;

    public CartItemModel(FoodModel food) {
        this.food = food;
    }

    public CartItemModel(FoodModel food, int count) {
        this.food = food;
        this.count = count;
    }
}
