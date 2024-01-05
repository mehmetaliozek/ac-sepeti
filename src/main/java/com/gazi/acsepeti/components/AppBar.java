package com.gazi.acsepeti.components;

import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**@see AppBar sınıfı
 * @see StackPane sınıfından kalıttık çünkü StackPane sınıfı içinedeki componentları sağa, sola, yukarı,
 * aşağı, merkeze vb yerleştirmemizi sağlıyor böylece uygulama başlığı bi kenarda dururken diğer tarafta
 * etkileşime geçceğimiz butonlar yer alıyor
 * @see IGeneralComponentsFunctions interface inden implement ettik
 */
public class AppBar extends StackPane implements IGeneralComponentsFunctions {
    private final Label titleLabel;
    // Sepete ve kullanıcı hesap ayarlarına geçiş için butonları içinde barındırır
    private final Actions actions;

    /**
     * @param title Appbarın başlığını tanımlar uygulamanın adını yazmak için kullanırız
     * @param actions sepet ve kullanıcı hesap sayfasına geçişleri içinde barındıran component
     * @param labelEvent Appbarın başlığına tıklancağında gerçekleşcek olaydır
     * @param cartEvent actionsın sepet butonuna tıklancağında gerçekleşcek olaydır
     * @param userEvent actionsın kullanıcı butonuna tıklancağında gerçekleşcek olaydır
     */
    public AppBar(String title, EventHandler<MouseEvent> labelEvent, EventHandler<ActionEvent> cartEvent, EventHandler<ActionEvent> userEvent) {
        titleLabel = new Label(title);
        titleLabel.setId("appBarTitle");
        titleLabel.setOnMouseClicked(labelEvent);

        actions = new Actions(cartEvent, userEvent);

        setThis();
    }

    // Kendini ayarlama
    @Override
    public void setThis() {
        getChildren().addAll(titleLabel, actions);
        setId("appBar");
        setAlignment(titleLabel, Pos.CENTER_LEFT);
        setAlignment(actions, Pos.CENTER_RIGHT);
        setPadding(new Insets(10, 40, 10, 40));
    }
}
