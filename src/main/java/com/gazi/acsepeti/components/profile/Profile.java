package com.gazi.acsepeti.components.profile;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.ISignFunctions;
import com.gazi.acsepeti.models.UserModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Profile extends VBox implements IGeneralComponentsFunctions, ISignFunctions {
    private UserModel userModel;

    public Profile() {
        userModel = new UserModel(0, "", "", "", "", "", "");
        createTitle();
        createTextField();
        createSignInButton();
        switchSign();
        setThis();
    }

    @Override
    public void setThis() {
        getStyleClass().add("food");
        setPrefHeight(600);
        setMaxWidth(600);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
    }

    @Override
    public void createTitle() {
        Label title = new Label("Hesabım");
        title.setId("appBarTitle");
        title.setPadding(new Insets(0, 0, 100, 0));
        getChildren().add(title);
    }

    @Override
    public void createTextField() {
        String[] prompTexts = new String[]{
                "Ad", "Soyad", "Email", "Şifre", "Adres", "Telefon"
        };

        for (int i = 0; i < prompTexts.length; i++) {
            if (i == 3) {
                PasswordField passwordField = new PasswordField();
                passwordField.getStyleClass().add("textfield");
                passwordField.setMaxWidth(400);
                passwordField.setPrefHeight(50);
                passwordField.setMaxHeight(200);
                passwordField.setPromptText(prompTexts[i]);
                getChildren().add(passwordField);
                continue;
            }
            TextField textField = new TextField();
            textField.getStyleClass().add("textfield");
            textField.setMaxWidth(400);
            textField.setPrefHeight(50);
            textField.setMaxHeight(200);
            textField.setPromptText(prompTexts[i]);
            getChildren().add(textField);
        }
    }

    @Override
    public void createSignInButton() {
        Button button = new Button("Kaydet");
        button.setId("shoppingBtn");
        button.setMaxWidth(200);
        button.setOnMouseClicked(event -> {
            getChildren().forEach(node -> {
                if (node.toString().contains("Field")) {
                    switch (((TextField) node).getPromptText()) {
                        case "Ad":
                            userModel.name = ((TextField) node).getText();
                            break;
                        case "Soyad":
                            userModel.surname = ((TextField) node).getText();
                            break;
                        case "Email":
                            userModel.mail = ((TextField) node).getText();
                            break;
                        case "Şifre":
                            userModel.password = ((TextField) node).getText();
                            break;
                        case "Adres":
                            userModel.address = ((TextField) node).getText();
                            break;
                        case "Telefon":
                            userModel.tel = ((TextField) node).getText();
                            break;
                    }
                }
            });
            //TODO:Kullanıcıyı güncelleyin
            Main.userModel = userModel;
            Main.getProfile();
        });
        getChildren().add(button);
    }

    @Override
    public void switchSign() {

    }
}
