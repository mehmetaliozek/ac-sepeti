package com.gazi.acsepeti.components;

import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

/**@see Actions sınıfı
 * @see StackPane sınıfından kalıttık çünkü StackPane sınıfı içinedeki componentları sağa, sola, yukarı,
 * aşağı, merkeze vb yerleştirmemizi sağlıyor
 * @see IGeneralComponentsFunctions interface inden implement ettik
 */
public class Actions extends StackPane implements IGeneralComponentsFunctions {
    private final Button cart;
    private final Button user;

    /**
     * @param cart Kullanıcının sepetine erişmesini sağlamak içiin oluşturulmuş bir butondur
     * @param user Kullanıcının bilgilerine erişmesi için oluşturulmuş bir butondur
     * @param cartEvent Actionsın sepet butonuna tıklancağında gerçekleşcek olaydır
     * @param userEvent Actionsın kullanıcı butonuna tıklancağında gerçekleşcek olaydır
     */
    public Actions(EventHandler<ActionEvent> cartEvent,EventHandler<ActionEvent> userEvent) {
        cart = createActionButton(FontAwesomeIcon.SHOPPING_CART);
        cart.setOnAction(cartEvent);
        user = createActionButton(FontAwesomeIcon.USER_CIRCLE);
        user.setOnAction(userEvent);
        setThis();
    }

    // Kendini ayarlama
    @Override
    public void setThis() {
        setMaxWidth(90);
        getChildren().addAll(cart, user);
        setAlignment(cart, Pos.CENTER_LEFT);
        setAlignment(user, Pos.CENTER_RIGHT);
    }

    // Butonlara işlevlerinin anlaşılması için ikon eklemek ve genel ayarlarının yapıldığı fonksiyon
    private Button createActionButton(FontAwesomeIcon glyph) {
        FontAwesomeIconView icon = new FontAwesomeIconView(glyph);
        icon.setSize("25");
        icon.getStyleClass().add("icon");

        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("action-btn");

        return button;
    }
}
