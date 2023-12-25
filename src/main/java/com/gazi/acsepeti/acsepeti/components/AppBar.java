package com.gazi.acsepeti.acsepeti.components;

import com.gazi.acsepeti.acsepeti.interfaces.IGeneralComponentsFunctions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class AppBar extends StackPane implements IGeneralComponentsFunctions {
    // Appbarın başlığını tanımlar uygulamanın adını yazmak için kullanırız
    private final Label titleLabel;
    // Sepete ve kullanıcı hesap ayarlarına geçiş için butonları içinde barındırır
    private final Actions actions;

    public AppBar(String title, EventHandler<MouseEvent> labelEvent, EventHandler<ActionEvent> cartEvent, EventHandler<ActionEvent> userEvent) {
        titleLabel = new Label(title);
        titleLabel.setId("appBarTitle");
        titleLabel.setOnMouseClicked(labelEvent);

        actions = new Actions(cartEvent, userEvent);

        thisSets();
    }

    @Override
    public void thisSets() {
        getChildren().addAll(titleLabel, actions);
        setId("appBar");
        setAlignment(titleLabel, Pos.CENTER_LEFT);
        setAlignment(actions, Pos.CENTER_RIGHT);
        setPadding(new Insets(10, 40, 10, 40));
    }
}
