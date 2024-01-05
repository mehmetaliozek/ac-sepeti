package com.gazi.acsepeti.models;

import java.util.ArrayList;

public class UserModel {
    public int id;

    public String name;
    public String surname;
    public String mail;
    public String password;
    public String address;
    public String tel;
    public ArrayList<CartItemModel> cart;

    public UserModel(int id, String name, String surname, String mail, String password, String address, String tel) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.tel = tel;
        this.cart = new ArrayList<CartItemModel>();
    }
    public UserModel(int id, String name, String surname, String mail, String password, String address, String tel,ArrayList<CartItemModel> cart) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.tel = tel;
        this.cart = cart;
    }

    public void addToCart(CartItemModel item) {
        //Cart dolu ve var olan bi ürünün sayısı artıyor ise
        if (cart.contains(item)) {
            if (changeAmountCartItem(item, 1)) return;
        }

        //Cart dolu ve yeni ürün ekleniyor ise
        cart.add(item);

        System.out.println(cart.size());
    }

    public boolean changeAmountCartItem(CartItemModel item, int amount) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).food.id == item.food.id) {
                cart.get(i).count += amount;
                //Azaltma işlemi uygulanırsa sepetten temelli silsin
                if (cart.get(i).count == 0) {
                    cart.remove(i);
                }
                return true;
            }
        }
        return false;
    }

}
