package com.gazi.acsepeti.interfaces;

// Kullan�c� giri� i�lemlerinin ger�ekle�tirildi�i sayfalar�n olu�turulmas�nda kullan�lan interface
public interface ISignFunctions {

    // Sayfan�n ba�l���n�n olu�turulmas� i�in
    void createTitle();
    // Sayfadaki textfieldlar�n olu�turulmas�
    void createTextField();
    // Sayfadaki butonun olu�turulmas�
    void createButton();
    // Oturum a�ma ve hesap olu�turma sayfalar� aras�nda ge�i� i�in bir fonksiyon
    void switchSign();
}
