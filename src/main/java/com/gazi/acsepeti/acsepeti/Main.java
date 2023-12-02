package com.gazi.acsepeti.acsepeti;

import com.gazi.acsepeti.acsepeti.components.Actions;
import com.gazi.acsepeti.acsepeti.components.AppBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // AppBar nesnesinin oluşturulması ve ekranda başka bir componenta yerleştirilmesi
        AppBar appBar = new AppBar("Aç Sepeti",new Actions());
        VBox vBox = new VBox(appBar);
        vBox.setStyle("-fx-background-color:#F0FAF9");

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
}
