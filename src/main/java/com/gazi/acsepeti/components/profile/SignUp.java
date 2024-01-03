package com.gazi.acsepeti.components.profile;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.ISignFunctions;
import com.gazi.acsepeti.models.CartItemModel;
import com.gazi.acsepeti.models.UserModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParsingException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            saveUser(userModel);
            Main.userModel = userModel;

            Main.getProfile();
        });
        getChildren().add(button);
    }

    private void saveUser(UserModel userModel) {
        // Önce mevcut kullanıcıları dosyadan oku
        JsonArray existingUsers = readJsonArrayFromFile("src/main/java/com/gazi/acsepeti/data/users.json");

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (JsonValue value : existingUsers) {
            builder.add(value);
        }
        // Yeni kullanıcıyı JSON dizisine ekleyelim
        JsonObject newUser = createJsonObject(userModel);
        builder.add(newUser);

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
