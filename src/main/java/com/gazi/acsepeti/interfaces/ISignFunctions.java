package com.gazi.acsepeti.interfaces;

// Kullanýcý giriþ iþlemlerinin gerçekleþtirildiði sayfalarýn oluþturulmasýnda kullanýlan interface
public interface ISignFunctions {

    // Sayfanýn baþlýðýnýn oluþturulmasý için
    void createTitle();
    // Sayfadaki textfieldlarýn oluþturulmasý
    void createTextField();
    // Sayfadaki butonun oluþturulmasý
    void createButton();
    // Oturum açma ve hesap oluþturma sayfalarý arasýnda geçiþ için bir fonksiyon
    void switchSign();
}
