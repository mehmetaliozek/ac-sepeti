package com.gazi.acsepeti;

import com.gazi.acsepeti.components.AppBar;
import com.gazi.acsepeti.components.Body;
import com.gazi.acsepeti.components.cart.Cart;
import com.gazi.acsepeti.components.cart.CartItem;
import com.gazi.acsepeti.components.profile.Sign;
import com.gazi.acsepeti.components.restaurant.Restaurant;
import com.gazi.acsepeti.models.FoodModel;
import com.gazi.acsepeti.models.RestaurantModel;
import com.gazi.acsepeti.models.UserModel;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    public static boolean sign = false;
    public static UserModel userModel;
    public static VBox vBox;
    private final int maxHorizontalRestaurantCount = 3;
    private final int maxHorizontalFoodCount = 2;


    private static void setBody(Node node) {
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        vBox.getChildren().add(node);
    }

    public static void getCart() {

        double total = 0;
        ArrayList cartItem = new ArrayList<CartItem>();
        try {
            for (int i = 0; i < userModel.cart.size(); i++) {
                cartItem.add(new CartItem(userModel.cart.get(i)));
                total += userModel.cart.get(i).food.price * userModel.cart.get(i).count;
            }
        } catch (Exception e) {

        }

        Cart cart = new Cart(cartItem, 1, String.format("%.2f", total));

        setBody(cart);

    }

    public static void getProfile() {
        Sign profile = new Sign();
        setBody(profile);
    }


    @Override
    public void start(Stage primaryStage) {
        //userModel = new UserModel(0, "Mehmet Ali", "Özek", "mehmetaliozek761@gmail.com", "123456", "", "");
        ArrayList restaurants = new ArrayList<Restaurant>();

        //TODO: Aşağıdaki for döngüsününden önce json okuma işlemlerini ekliyip gelen data ile aşağıdaki for döngüsünü çalıştırın
        for (int i = 0; i < 7; i++) {
            ArrayList<FoodModel> foodModelList = new ArrayList<>();
            for (int j = 0; j < new Random().nextInt(2, 10); j++) {
                FoodModel foodModel = new FoodModel(j, "Döner Tabağı", "Taze döner eti, pilav ve yanında garnitürlerle servis edilen lezzetli döner tabağı.", "https://i.pinimg.com/564x/1f/9c/73/1f9c739dc37c80ffb3cc68e8441c7f34.jpg", 124.99);
                foodModelList.add(foodModel);
            }
            RestaurantModel restaurantModel = new RestaurantModel(0, "Etlekmekçi Hans Usta", "https://cdn.yemek.com/mnresize/1250/833/uploads/2021/09/yemeksepeti-lahmacun-yemekcom.jpg", foodModelList);
            Restaurant restaurant = new Restaurant(restaurantModel);
            restaurants.add(restaurant);
        }

        // Uygulamanın sayfalarının oluşturulması nesnesinin oluşturulması
        Body body = new Body(restaurants, maxHorizontalRestaurantCount);

        // AppBar nesnesinin oluşturulması
        AppBar appBar = new AppBar("Aç Sepeti", mouseEvent -> {
            setBody(body);
        }, cartEvent -> {
            getCart();
        }, userEvent -> {
            getProfile();
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
