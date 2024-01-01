package com.gazi.acsepeti.components.restaurant;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.components.Body;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.IRestaurantFunctions;
import com.gazi.acsepeti.models.RestaurantModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Restaurant extends VBox implements IGeneralComponentsFunctions, IRestaurantFunctions {
    private RestaurantModel model;
    private final EventHandler<MouseEvent> mouseEventHandler;

    public Restaurant(RestaurantModel model) {
        this.model = model;
        this.mouseEventHandler = e -> {
            // TODO: Yemek listesine geçiş sağla aşağıda örnek sistem var
            ArrayList foods = new ArrayList<Food>();
            for (int i = 0; i < model.foods.size(); i++) {
                foods.add(new Food(model.foods.get(i)));
            }
            Body body = new Body(foods, 2);
            Main.vBox.getChildren().remove(Main.vBox.getChildren().size() - 1);
            Main.vBox.getChildren().add(body);
        };
        setImage();
        setAction();
        setThis();
    }

    @Override
    public void setThis() {
        getStyleClass().add("restaurant");
        setPrefHeight(300);
        setPrefWidth(405);
        setAlignment(Pos.BASELINE_CENTER);
        setPadding(new Insets(10, 0, 0, 0));
        setEffect(new DropShadow(5, 0, 0, Color.GRAY));
        setOnMouseEntered(event -> setEffect(new DropShadow(30, 0, 0, Color.GRAY)));
        setOnMouseExited(event -> setEffect(new DropShadow(5, 0, 0, Color.GRAY)));
        setOnMouseClicked(mouseEventHandler);
    }

    @Override
    public void setImage() {
        // Resmi internetten çekiyoz
        Image image = new Image(model.imageUrl);

        // Resmi görüntüleyici bileşene aktarıyoz
        ImageView imageView = new ImageView(image);

        // Resmin genişlik ve yükseklik değerlerini giriyoz
        imageView.setFitWidth(385);
        imageView.setFitHeight(200);

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
        pane.setPadding(new Insets(15, 10, 0, 10));

        Label lbl = new Label(model.name);
        lbl.setId("appBarTitle");

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
        icon.setSize("25");
        icon.getStyleClass().add("icon");

        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("restaurant-btn");
        button.setOnMouseClicked(mouseEventHandler);

        pane.getChildren().addAll(lbl, button);
        StackPane.setAlignment(lbl, Pos.CENTER_LEFT);
        StackPane.setAlignment(button, Pos.CENTER_RIGHT);

        getChildren().add(pane);
    }
}
