package com.gazi.acsepeti.components.profile;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class Sign extends ScrollPane implements IGeneralComponentsFunctions {
    private StackPane pane;

    public Sign() {
        pane = new StackPane();
        if (Main.userModel == null) {
            if (Main.sign == false) {
                SignIn signIn = new SignIn();
                pane.getChildren().add(signIn);
                pane.setAlignment(signIn, Pos.CENTER);
            } else {
                SignUp signUp = new SignUp();
                pane.getChildren().add(signUp);
                pane.setAlignment(signUp, Pos.CENTER);
            }
        } else {
            Profile profile = new Profile();
            pane.getChildren().add(profile);
            pane.setAlignment(profile, Pos.CENTER);
        }
        setThis();
    }

    @Override
    public void setThis() {
        setContent(pane);
        pane.setStyle("-fx-background-color: #FDEDF1");
        pane.setPrefWidth(1250);
        pane.setPadding(new Insets(10));
        setStyle("-fx-background-insets: 0, 1");
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setPrefHeight(670);
        setPrefWidth(1280);
    }
}
