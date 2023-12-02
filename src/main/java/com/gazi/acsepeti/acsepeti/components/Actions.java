package com.gazi.acsepeti.acsepeti.components;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class Actions extends StackPane {

    public Actions() {
        Button cart = createActionButton(FontAwesomeIcon.SHOPPING_CART);
        Button user = createActionButton(FontAwesomeIcon.USER_CIRCLE);

        setMaxWidth(90);
        getChildren().addAll(cart, user);
        setAlignment(cart, Pos.CENTER_LEFT);
        setAlignment(user, Pos.CENTER_RIGHT);
    }

    private Button createActionButton(FontAwesomeIcon glyph){
        FontAwesomeIconView icon =  new FontAwesomeIconView(glyph);
        icon.setSize("25");
        icon.getStyleClass().add("icon");

        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("action-btn");

        return button;
    }
}
