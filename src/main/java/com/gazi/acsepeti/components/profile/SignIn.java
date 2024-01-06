package com.gazi.acsepeti.components.profile;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.IRestaurantFunctions;
import com.gazi.acsepeti.interfaces.ISignFunctions;
import com.gazi.acsepeti.models.UserModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**@see SignIn sınıfı
 * @see VBox sınıfından kalıttık çünkü eklediğimiz componentlar otomatik bir şekilde alt alta diziliyor
 * @see IGeneralComponentsFunctions interface inden implement ettik
 * @see IRestaurantFunctions interface inden implement ettik
 */
public class SignIn extends VBox implements IGeneralComponentsFunctions, ISignFunctions {
    private UserModel userModel;

    /**
     * @param userModel giriş yapan kullanıcının bilgilerini içinde barındıran sınıf
     */
    public SignIn() {
        userModel = new UserModel(0, "", "", "", "", "", "");
        createTitle();
        createTextField();
        createButton();
        switchSign();
        setThis();
    }

    // Kendini ayarlama
    @Override
    public void setThis() {
        getStyleClass().add("food");
        setPrefHeight(600);
        setMaxWidth(600);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
    }

    // Sayfanın başlığının oluşturulması
    @Override
    public void createTitle() {
        Label title = new Label("Oturum Aç");
        title.setId("appBarTitle");
        title.setPadding(new Insets(0, 0, 100, 0));
        getChildren().add(title);
    }

    // Sayfadaki textfieldların oluşturulması
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

    // Sayfadaki butonun oluşturulması
    @Override
    public void createButton() {
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
            boolean userExists = false;
            try {
                userExists = Main.helper.checkUserExistence(userModel.mail, userModel.password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (userExists) {
                Main.getHome();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("EMAİ VEYA ŞİFRE YANLIŞ");
                alert.show();
                // Handle the case when the user does not exist
            }
        });
        getChildren().add(button);
    }

    // Oturum açma ve hesap oluşturma sayfaları arasında geçiş için bir fonksiyon
    @Override
    public void switchSign() {
        Hyperlink link = new Hyperlink("Hesap Oluştur");
        link.setStyle("-fx-text-fill: #FDEDF1;");
        link.setPadding(new Insets(25, 0, 0, 0));
        link.setOnMouseClicked(event -> {
            Main.sign = !Main.sign;
            Main.getHome();
        });
        getChildren().add(link);
    }
}
