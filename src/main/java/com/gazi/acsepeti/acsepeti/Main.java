package com.gazi.acsepeti.acsepeti;

import com.gazi.acsepeti.acsepeti.components.AppBar;
import com.gazi.acsepeti.acsepeti.components.Body;
import com.gazi.acsepeti.acsepeti.components.Restaurant;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static VBox vBox;
    private final int maxHorizontalRestaurantCount = 3;
    private final int maxHorizontalFoodCount = 2;

    private void getBody(Node node) {
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        vBox.getChildren().add(node);
    }

    @Override
    public void start(Stage primaryStage) {
        ArrayList restaurants = new ArrayList<Restaurant>();

        for (int i = 0; i < 7; i++) {
            Restaurant restaurant = new Restaurant("Etlekmekçi Hans Usta", "https://cdn.yemek.com/mnresize/1250/833/uploads/2021/09/yemeksepeti-lahmacun-yemekcom.jpg");
            restaurants.add(restaurant);
        }

        // Uygulamanın sayfalarının oluşturulması nesnesinin oluşturulması
        Body body = new Body(restaurants, maxHorizontalRestaurantCount);

        // AppBar nesnesinin oluşturulması
        AppBar appBar = new AppBar("Aç Sepeti", mouseEvent -> {
            // TODO: Ana sayfayı geri yükleme eklencek
            getBody(body);
        }, cartEvent -> {
            // TODO: Kullanıcının sepetine geçiş eklencek
            getBody(body);
        }, userEvent -> {
            // TODO: Kullanıcının profil sayfasına geçiş eklencek
            getBody(body);
        });

        // appBar ve body nin ekranda yukardan aşağı doğru yerleşmesi için vbox a ekleniyor
        vBox = new VBox(appBar, body);
        vBox.setId("vBox");

        // Sahnenin hazırlanması
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("styles.css");

        // Pencerenin ayarlanması
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Aç Sepeti");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
