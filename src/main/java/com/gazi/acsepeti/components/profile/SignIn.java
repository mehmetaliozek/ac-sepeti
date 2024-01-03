package com.gazi.acsepeti.components.profile;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.ISignFunctions;
import com.gazi.acsepeti.models.UserModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;

public class SignIn extends VBox implements IGeneralComponentsFunctions, ISignFunctions {
    private UserModel userModel;

    public SignIn() {
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
                "Email", "Şifre"
        };

        TextField textField = new TextField();
        textField.getStyleClass().add("textfield");
        textField.setMaxWidth(400);
        textField.setPrefHeight(50);
        textField.setMaxHeight(200);
        textField.setPromptText(prompTexts[0]);
        getChildren().add(textField);


        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("textfield");
        passwordField.setMaxWidth(400);
        passwordField.setPrefHeight(50);
        passwordField.setMaxHeight(200);
        passwordField.setPromptText(prompTexts[1]);
        getChildren().add(passwordField);
    }

    @Override
    public void createSignInButton() {
        Button button = new Button("Giriş Yap");
        button.setId("shoppingBtn");
        button.setMaxWidth(200);
        button.setOnMouseClicked(event -> {
            getChildren().forEach(node -> {
                if (node.toString().contains("Field")) {
                    switch (((TextField) node).getPromptText()) {
                        case "Email":
                            userModel.mail = ((TextField) node).getText();
                            break;
                        case "Şifre":
                            userModel.password = ((TextField) node).getText();
                            break;
                    }
                }
            });

            // TODO: Kullanıcının varlığını kontrol et
            boolean userExists = checkUserExistence(userModel.mail, userModel.password);

            if (userExists) {
                System.out.println("Giriş yapıldı");
                Main.getProfile();
            } else {
                System.out.println("Kullanıcı bulunamadı");
                // Handle the case when the user does not exist
            }
        });
        getChildren().add(button);
    }

    private boolean checkUserExistence(String email, String password) {
        try (JsonReader reader = Json.createReader(new FileReader("src/main/java/com/gazi/acsepeti/data/users.json"))) {
            JsonArray usersArray = reader.readArray();
            System.out.println(usersArray);
            for (JsonObject userObject : usersArray.getValuesAs(JsonObject.class)) {

                String storedEmail = userObject.getString("mail");
                System.out.println(storedEmail);
                String storedPassword = userObject.getString("password");
                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    Main.userModel = convertJsonObjectToUserModel(userObject);
                    System.out.println(Main.userModel.name);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
        return false;
    }
    private UserModel convertJsonObjectToUserModel(JsonObject userObject) {
        // Extract user information from the JsonObject and create a UserModel
        int id = userObject.getInt("id");
        String name = userObject.getString("name");
        String surname = userObject.getString("surname");
        String email = userObject.getString("mail");
        String password = userObject.getString("password");
        String address = userObject.getString("address");
        String tel = userObject.getString("tel");

        return new UserModel(id, name, surname, email, password, address, tel);
    }

    @Override
    public void switchSign() {
        Hyperlink link = new Hyperlink("Hesap Oluştur");
        link.setStyle("-fx-text-fill: #FDEDF1;");
        link.setPadding(new Insets(25, 0, 0, 0));
        link.setOnMouseClicked(event -> {
            Main.sign = !Main.sign;
            Main.getProfile();
        });
        getChildren().add(link);
    }
}
