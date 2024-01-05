package com.gazi.acsepeti.models;

import java.util.ArrayList;

/**
 * @param id kullanıcı idsinin tutuldu değişken
 * @param name kullanıcı adının tutuldu değişken
 * @param mail kullanıcı mailinin tutuldu değişken
 * @param password kullanıcı şifresinin tutuldu değişken
 * @param adress kullanıcı adresinin tutuldu değişken
 * @param tel kullanıcı telefon numarasının tutuldu değişken
 * @param cart kullanıcı sepetindeki ürünlerin tutuldu değişken
 */
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
        //Sepette var olan bi ürünün sayısı artıyor ise
        if (cart.contains(item)) {
            if (changeAmountCartItem(item, 1)) return;
        }

        //Sepete yeni ürün ekleniyor ise
        cart.add(item);
    }

    // Sepette belirtilen ürünün sayısını arttırıp azaltmaya yarar
    public boolean changeAmountCartItem(CartItemModel item, int amount) {
        // Aşağıdaki for aktif kullanıcının sepetindeki ürünlerin miktarı kadar döner
        for (int i = 0; i < cart.size(); i++) {
            /** Eğer
             * @param item sepette var ise miktari
             * @param amount kadar arttırılır amount pozitif ve negatif değer alabilir
             *               yani negatif artış yaptığımızda ürünün miktari azalmış olur
             */
            if (cart.get(i).food.id == item.food.id) {
                cart.get(i).count += amount;
                //Negatif artış ile ürünün miktarı sıfır olursa sepetten siliyor
                if (cart.get(i).count == 0) {
                    cart.remove(i);
                }
                return true;
            }
        }
        return false;
    }
}
