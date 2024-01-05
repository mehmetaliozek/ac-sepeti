package com.gazi.acsepeti.models;

import java.util.Objects;

/**
 * @param id yeme�in idsinin tutuldu de�i�ken
 * @param name yeme�in ad�n�n tutuldu de�i�ken
 * @param description yeme�in a��klamas�n�n tutuldu de�i�ken
 * @param imageUrl yeme�in resminin tutuldu de�i�ken
 * @param price yeme�in fiyat�n�n tutuldu de�i�ken
 */
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
