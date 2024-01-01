package com.gazi.acsepeti.components.cart;

import com.gazi.acsepeti.Main;
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

public class Cart extends Body {

    public Cart(ArrayList<Parent> components, int maxHorizontalComponentCount, String total) {
        super(components, maxHorizontalComponentCount);

        if (!components.isEmpty()) {
            Label totalLabel = new Label("Ara Toplam: " + total);
            totalLabel.setId("appBarTitle");

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

            StackPane pane = new StackPane(totalLabel, button);
            pane.getStyleClass().add("total");
            pane.setPrefWidth(410);
            pane.setPadding(new Insets(20));

            pane.setAlignment(totalLabel, Pos.TOP_CENTER);
            pane.setAlignment(button, Pos.BOTTOM_CENTER);

            getGridPane().add(pane, 1, 0);
        }
    }
}