package com.gazi.acsepeti.acsepeti.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class AppBar extends StackPane {
    public AppBar(String title, Node node) {

        Label titleLabel = new Label(title);
        titleLabel.setId("appBarTitle");

        getChildren().addAll(titleLabel, node);
        setId("appBar");
        setAlignment(titleLabel,Pos.CENTER_LEFT);
        setAlignment(node,Pos.CENTER_RIGHT);
        setPadding(new Insets(10, 40, 10, 40));
    }
}
