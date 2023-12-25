package com.gazi.acsepeti.acsepeti.components;

import com.gazi.acsepeti.acsepeti.interfaces.IGeneralComponentsFunctions;
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

public class Restaurant extends VBox implements IGeneralComponentsFunctions {
    private final String name;
    private final String imageUrl;
    private final EventHandler<MouseEvent> mouseEventHandler;

    public Restaurant(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.mouseEventHandler = e->{
            // TODO: Yemek listesine geçiş sağla aşağıda örnek sistem var
//            ArrayList restaurants = new ArrayList<Button>();
//            restaurants.add(new Button());
//            restaurants.add(new Button());
//            Body body = new Body(restaurants,2);
//            Main.vBox.getChildren().remove(Main.vBox.getChildren().size()-1);
//            Main.vBox.getChildren().add(body);
        };
        setImage();
        setAction();
        thisSets();
    }

    @Override
    public void thisSets() {
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

    private void setImage() {
        // Resmi internetten çekiyoz
        Image image = new Image(imageUrl);

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

    private void setAction() {
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(15, 10, 0, 10));

        Label lbl = new Label(name);
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
