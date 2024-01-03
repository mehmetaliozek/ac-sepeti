package com.gazi.acsepeti.components.profile;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.ISignFunctions;
import com.gazi.acsepeti.models.CartItemModel;
import com.gazi.acsepeti.models.UserModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.json.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
            updateUser(userModel);
            //Main.userModel = userModel;
            Main.getProfile();
        });
        getChildren().add(button);
    }
    private void updateUser(UserModel userModel) {
        // Önce mevcut kullanıcıları dosyadan oku
        JsonArray existingUsers = readJsonArrayFromFile("src/main/java/com/gazi/acsepeti/data/users.json");

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (JsonValue value : existingUsers) {
            if (value instanceof JsonObject) {
                JsonObject userObject = (JsonObject) value;
                String existingUserName = userObject.getString("name");
                System.out.println(existingUserName);

                // Eğer kullanıcının adı Main.userModel.name ile eşleşiyorsa, bilgileri güncelle
                System.out.println(Main.userModel.name);
                if (existingUserName.toString()==userModel.name.toString()) {
                    System.out.println("aaaaaaaaaaaaaaaaaaaaa");
                    JsonObject updatedUser = createJsonObject(userModel);
                    builder.add(updatedUser);
                } else {
                    builder.add(userObject);
                }
            } else {
                // Eğer değer bir JsonObject değilse, olduğu gibi ekleyin
                builder.add(value);
            }
        }

        // Güncellenmiş JSON dizisini dosyaya yazalım
        writeJsonArrayToFile(builder.build(), "src/main/java/com/gazi/acsepeti/data/users.json");
    }


    private JsonArray readJsonArrayFromFile(String filePath) {
        try (JsonReader reader = Json.createReader(new FileReader(filePath))) {
            // Dosyadan JSON dizisini okuma işlemi
            return reader.readArray();
        } catch (IOException e) {
            // Hata durumunda yeni bir boş JSON dizisi oluştur
            return Json.createArrayBuilder().build();
        }
    }

    private void writeJsonArrayToFile(JsonArray jsonArray, String filePath) {
        try (JsonWriter writer = Json.createWriter(new FileWriter(filePath))) {
            // JSON dizisini dosyaya yazma işlemi
            writer.writeArray(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject createJsonObject(UserModel userModel) {
        JsonArrayBuilder cartArrayBuilder = Json.createArrayBuilder();

        for (CartItemModel item : userModel.cart) {
            cartArrayBuilder.add((JsonValue) item);
        }

        JsonArray cartArray = cartArrayBuilder.build();
        return Json.createObjectBuilder()
                .add("id",userModel.id)
                .add("name", userModel.name)
                .add("surname", userModel.surname)
                .add("mail", userModel.mail)
                .add("password", userModel.password)
                .add("address",userModel.address)
                .add("tel",userModel.tel)
                .add("cart",cartArray )
                .build();
    }

    @Override
    public void switchSign() {

    }
}
