package com.gazi.acsepeti.components.profile;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.ISignFunctions;
import com.gazi.acsepeti.models.UserModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SignUp extends VBox implements IGeneralComponentsFunctions, ISignFunctions {
    private UserModel userModel;

    public SignUp() {
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
        Label title = new Label("Oturum Aç");
        title.setId("appBarTitle");
        title.setPadding(new Insets(0, 0, 100, 0));
        getChildren().add(title);
    }

    @Override
    public void createTextField() {
        String[] prompTexts = new String[]{
                "Ad", "Soyad", "Email", "Şifre"
        };

        for (int i = 0; i < 3; i++) {
            TextField textField = new TextField();
            textField.getStyleClass().add("textfield");
            textField.setMaxWidth(400);
            textField.setPrefHeight(50);
            textField.setMaxHeight(200);
            textField.setPromptText(prompTexts[i]);
            getChildren().add(textField);
        }

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("textfield");
        passwordField.setMaxWidth(400);
        passwordField.setPrefHeight(50);
        passwordField.setMaxHeight(200);
        passwordField.setPromptText(prompTexts[3]);
        getChildren().add(passwordField);
    }

    @Override
    public void createSignInButton() {
        Button button = new Button("Kaydol");
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
                    }
                }
            });
            //TODO:Kullanıcıyı kaydedin
            Main.userModel = userModel;
            Main.getProfile();
        });
        getChildren().add(button);
    }

    @Override
    public void switchSign() {
        Hyperlink link = new Hyperlink("Oturum Aç");
        link.setStyle("-fx-text-fill: #FDEDF1;");
        link.setPadding(new Insets(25, 0, 0, 0));
        link.setOnMouseClicked(event -> {
            Main.sign = !Main.sign;
            Main.getProfile();
        });
        getChildren().add(link);
    }
}
