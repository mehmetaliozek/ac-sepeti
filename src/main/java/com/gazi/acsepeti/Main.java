package com.gazi.acsepeti;

import java.io.FileNotFoundException;

import com.gazi.acsepeti.components.AppBar;
import com.gazi.acsepeti.components.Body;
import com.gazi.acsepeti.components.cart.Cart;
import com.gazi.acsepeti.components.cart.CartItem;
import com.gazi.acsepeti.components.profile.Sign;
import com.gazi.acsepeti.components.restaurant.Food;
import com.gazi.acsepeti.components.restaurant.Restaurant;
import com.gazi.acsepeti.models.FoodModel;
import com.gazi.acsepeti.models.UserModel;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    // Json okuma yazma işlemleri için geliştirilmiş sınıf
    public static JsonHelper helper = new JsonHelper();
    // Şu an aktif bir kullanıcı olup olmaması durumuna göre bir takım işlemleri gerçekleştirip kısıtlamamıza yarayan değişken
    public static boolean sign = false;
    // Giriş yapan kullanıcının bilgilerini saklayan sınıf
    public static UserModel userModel;
    // JavaFX in bize sağlamış olduğu bir sınıftır bu sınıf içine eklenen componentları alt alta dizilmesini sağlar
    public static VBox vBox;
    // Ana sayfanın gövdesi
    private static Body body;
    // Ana sayfada gösterilen restoranların yan yana en fazla kaç tane gelebilceğini ifade eder
    private final int maxHorizontalRestaurantCount = 3;
    // Herhangi bir restorana girildiği vakit o restoranın ana sayfasında yan yana en fazla kaç yemeğin çıkacağını temsil eder
    private final int maxHorizontalFoodCount = 2;

    // Sayfa geçişlerinde sayfanın güncellenmesini sağlayan fonksiyon
    public static void setBody(Node node) {
        /** Aşağıdaki işlemlerde
         * @param vBox ta sıralı olan elemanlardan sonuncusunu silip yerine yeni elemanı ekliyor
         *             bu şekilde ana sayfa değişmiş oluyor (Kullanıcı sepeti, Kullanıcı girişi, Restoran yemek listesi)
         */
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        vBox.getChildren().add(node);
    }

    // Kullanıcı sepetine yönlendiren fonksiyon
    public static void getCart() {
        /**@param total sepetteki ürünlerin toplam değeri */
        double total = 0;

        /**@param cartItem sepetteki ürünlerin bilgilerini gösteren component*/
        ArrayList cartItem = new ArrayList<CartItem>();

        // Aşağıdaki try-catch kullanıcı nesnesi boş ise hata çıkmasını önlüyor
        try {
            /** Aşağıdaki for döngüsü ile kullanıcının sepetindeki ürünleri
             * @param cartItem arrayını ekliyoruz ardındanda
             * @param total değişkenine ürünün fiyatını ekliyip toplam fiyatı elde ediyoruz
             */
            for (int i = 0; i < userModel.cart.size(); i++) {
                cartItem.add(new CartItem(userModel.cart.get(i)));
                total += userModel.cart.get(i).food.price * userModel.cart.get(i).count;
            }
        } catch (Exception e) {

        }

        /** Kullanıcı sepetindeki ürünleri
         * @param cartItem a ekledikten sonra sepet sayfasını olusturcak olan
         * @param cart nesnesini oluşturuyoruz bu sınıfı setBody fonksiyonuna parametre olarak veriyoz
         *             ve ana sayfadan kullanıcı sepeti sayfasına geçmiş oluyoz
         */
        Cart cart = new Cart(cartItem, 1, String.format("%.2f", total));

        setBody(cart);
    }

    // Kullanıcı giriş yapma sayfasına yönlendiren fonksiyon
    public static void getProfile() {
        Sign profile = new Sign();
        setBody(profile);
    }

    public static void getHome() {
        setBody(body);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ArrayList restaurants = new ArrayList<Restaurant>();

        helper.readDataFromRestaurants(restaurants);

        // Uygulamanın sayfalarının oluşturulması nesnesinin oluşturulması
        body = new Body(restaurants, maxHorizontalRestaurantCount);

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

    @Override
    public void stop() throws Exception {
        // Program kapatılırken aktif kullanıcının verilerini kaydediyoruz
        helper.updateUser(userModel);
    }
}