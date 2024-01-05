package com.gazi.acsepeti.components.restaurant;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.IRestaurantFunctions;
import com.gazi.acsepeti.models.CartItemModel;
import com.gazi.acsepeti.models.FoodModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends HBox implements IGeneralComponentsFunctions, IRestaurantFunctions {
    private FoodModel model;

    public Food(FoodModel model) {
        this.model = model;

        setImage();
        setAction();
        setThis();
    }

    @Override
    public void setThis() {
        getStyleClass().add("food");
        setPrefHeight(250);
        setPrefWidth(608);
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(10));
        setEffect(new DropShadow(5, 0, 0, Color.GRAY));
        setOnMouseEntered(event -> setEffect(new DropShadow(30, 0, 0, Color.GRAY)));
        setOnMouseExited(event -> setEffect(new DropShadow(5, 0, 0, Color.GRAY)));
    }

    @Override
    public void setImage() {
        // Resmi internetten çekiyoz
        Image image = new Image(model.imageUrl);

        // Resmi görüntüleyici bileşene aktarıyoz
        ImageView imageView = new ImageView(image);

        // Resmin genişlik ve yükseklik değerlerini giriyoz
        imageView.setFitWidth(304);
        imageView.setFitHeight(230);

        // Resim köşeleri kavisli olsun diye kırpıyoz
        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );

        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imageView.setClip(clip);

        // Resmi Restaurant bileşenine ekliyoz
        getChildren().add(imageView);
    }

    @Override
    public void setAction() {
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(10));

        Label titleLabel = new Label(model.name);
        titleLabel.setId("appBarTitle");

        Label descriptionLabel = new Label(model.description);
        descriptionLabel.setId("foodDesciption");
        descriptionLabel.setWrapText(true);

        Label priceLabel = new Label(Double.toString(model.price));
        priceLabel.setId("foodPrice");

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_SQUARE);
        icon.setSize("35");
        icon.getStyleClass().add("icon");

        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("restaurant-btn");
        button.setOnMouseClicked(event -> {
            CartItemModel item = new CartItemModel(model);
            try {
                Main.userModel.addToCart(item);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("ÜRÜN SEPETE EKLENMİŞTİR");
                alert.show();
                System.out.println(Main.userModel.cart.size());
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("SEPETE ÜRÜN EKLEYEBİLMEK İÇİN GİRİŞ YAP VEYA KAYIT OL");
                alert.show();
            }
        });

        pane.getChildren().addAll(titleLabel, descriptionLabel, priceLabel, button);
        StackPane.setAlignment(titleLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(descriptionLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(priceLabel, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);

        getChildren().add(pane);
    }
}
