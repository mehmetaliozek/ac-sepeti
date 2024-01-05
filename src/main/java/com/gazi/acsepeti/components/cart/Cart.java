package com.gazi.acsepeti.components.cart;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.components.Actions;
import com.gazi.acsepeti.components.Body;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.models.CartItemModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**@see Cart sınıfı
 * @see Body sınıfından kalıttık çünkü bu Cart bileşeninin görünüm olarak yarısı Body ile aynı olcaktı
 */
public class Cart extends Body {

    /**
     * @param components kullanıcının sepete eklediği ürünler
     * @param maxHorizontalComponentCount sepetteki ürünlerin yan yana kaç tane gözükçeği
     * @param total sepetteki ürünlerin toplam fiyatı
     */
    public Cart(ArrayList<Parent> components, int maxHorizontalComponentCount, String total) {
        super(components, maxHorizontalComponentCount);

        // Sepet boş değilse toplam fiyatı gösteren bileşen gözüyor
        // Sepet boş ise ekran bomboş kalıyor
        if (!components.isEmpty()) {
            /**@param totalLabel toplam fiyat değerini göstercek*/
            Label totalLabel = new Label("Ara Toplam: " + total);
            totalLabel.setId("appBarTitle");

            /**@param button tıklandığında alışveriş tamamlancak*/
            Button button = new Button("Alışverişi Tamamla");
            button.setId("shoppingBtn");
            button.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Alışverişiniz tamamlandı. Afiyet olsun");
                alert.setOnCloseRequest(event1 -> {
                    Main.userModel.cart.removeAll(Main.userModel.cart);
                    Main.getCart();
                });
                alert.show();
            });

            /**@param pane bu bileşen içinde toplam değerini ve alışverişi tamamlama butonunu içercek*/
            StackPane pane = new StackPane(totalLabel, button);
            pane.getStyleClass().add("total");
            pane.setPrefWidth(410);
            pane.setPadding(new Insets(20));

            pane.setAlignment(totalLabel, Pos.TOP_CENTER);
            pane.setAlignment(button, Pos.BOTTOM_CENTER);

            // StackPane'i Cart bileşenimize ekleme
            getGridPane().add(pane, 1, 0);
        }
    }
}