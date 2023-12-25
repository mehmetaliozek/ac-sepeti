package com.gazi.acsepeti.acsepeti.components;

import com.gazi.acsepeti.acsepeti.interfaces.IGeneralComponentsFunctions;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Body extends ScrollPane implements IGeneralComponentsFunctions {
    private final GridPane gridPane;
    private final int maxHorizontalComponentCount;
    private int positionX = 0;
    private int positionY = 0;

    public Body(ArrayList<Parent> components, int maxHorizontalComponentCount) {
        this.maxHorizontalComponentCount = maxHorizontalComponentCount;
        this.gridPane = new GridPane();

        addComponents(components);
        thisSets();
    }

    @Override
    public void thisSets() {
        setContent(gridPane);
        gridPane.setStyle("-fx-background-color: #FDEDF1");
        gridPane.setPrefWidth(1250);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        setStyle("-fx-background-insets: 0, 1");
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setPrefHeight(670);
        setPrefWidth(1280);
    }

    private void addComponents(ArrayList<Parent> components) {
        components.forEach(component -> {
            gridPane.add(component, positionX, positionY);
            positionX++;
            if (positionX == maxHorizontalComponentCount) {
                positionX = 0;
                positionY++;
            }
        });
    }
}
