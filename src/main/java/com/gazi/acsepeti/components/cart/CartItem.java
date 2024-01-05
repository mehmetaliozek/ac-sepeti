package com.gazi.acsepeti.components.cart;

import com.gazi.acsepeti.Main;
import com.gazi.acsepeti.components.restaurant.Food;
import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import com.gazi.acsepeti.interfaces.IRestaurantFunctions;
import com.gazi.acsepeti.models.CartItemModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**@see CartItem sınıfı
 * @see HBox sınıfından kalıttık çünkü eklediğimiz componentlar otomatik bir şekilde yan yana diziliyor
 * @see IGeneralComponentsFunctions interface inden implement ettik
 * @see IRestaurantFunctions interface inden implement ettik
 */
public class CartItem extends HBox implements IGeneralComponentsFunctions, IRestaurantFunctions {

    private CartItemModel model;

    /**
     * @param model sepetteki ürünle ilgili bilgileri içinde barındıran sınıf
     */
    public CartItem(CartItemModel model) {
        this.model = model;

        setImage();
        setAction();
        setThis();
    }

    // Kendini ayarlama
    @Override
    public void setThis() {
        getStyleClass().add("food");
        setPrefHeight(250);
        setPrefWidth(800);
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(10));
        setEffect(new DropShadow(5, 0, 0, Color.GRAY));
        setOnMouseEntered(event -> setEffect(new DropShadow(30, 0, 0, Color.GRAY)));
        setOnMouseExited(event -> setEffect(new DropShadow(5, 0, 0, Color.GRAY)));
    }

    // İçeriğindeki resmin ayarlanması
    @Override
    public void setImage() {
        // Resmi internetten çekiyoz
        Image image = new Image(model.food.imageUrl);

        // Resmi görüntüleyici bileşene aktarıyoz
        ImageView imageView = new ImageView(image);

        // Resmin genişlik ve yükseklik değerlerini giriyoz
        imageView.setFitWidth(304);
        imageView.setFitHeight(230);

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
        /**@param pane bu bileşen içinde yemek bilgileri ve ürün miktarını değiştirme butonunu tutacak*/
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(10));

        /**@param titleLabel yemeğin adını gösterdiğimiz bileşen*/
        Label titleLabel = new Label(model.food.name);
        titleLabel.setId("appBarTitle");

        /**@param descriptionLabel yemeğin açıklaması gösterdiğimiz bileşen*/
        Label descriptionLabel = new Label(model.food.description);
        descriptionLabel.setId("foodDesciption");
        descriptionLabel.setWrapText(true);

        /**@param priceLabel yemeğin fiyatını gösterdiğimiz bileşen*/
        Label priceLabel = new Label("Toplam Fiyat: " + String.format("%.2f", model.food.price * model.count));
        priceLabel.setId("foodPrice");

        /**@param increaseButton yemek miktarını arttıran buton*/
        Button increaseButton = createChangeAmountButton(FontAwesomeIcon.PLUS_SQUARE, 1);

        /**@param countLabel yemek miktarını gösteren label*/
        Label countLabel = new Label(Integer.toString(model.count));
        countLabel.setId("appBarTitle");

        /**@param reduceButton yemek miktarını azaltan buton*/
        Button reduceButton = createChangeAmountButton(FontAwesomeIcon.MINUS_SQUARE, -1);

        /**@param buttons içinde yemek miktarını değiştiren butonları ve yemek miktarını tutacak olan bileşen */
        StackPane buttons = new StackPane(reduceButton, countLabel, increaseButton);
        buttons.setAlignment(reduceButton, Pos.BOTTOM_LEFT);
        buttons.setAlignment(countLabel, Pos.BOTTOM_CENTER);
        buttons.setAlignment(increaseButton, Pos.BOTTOM_RIGHT);
        buttons.setMaxWidth(135);

        // StackPane'e bileşenleri ekleme ve konumlarını ayarlama
        pane.getChildren().addAll(titleLabel, descriptionLabel, priceLabel, buttons);
        pane.setAlignment(titleLabel, Pos.TOP_LEFT);
        pane.setAlignment(descriptionLabel, Pos.CENTER_LEFT);
        pane.setAlignment(priceLabel, Pos.BOTTOM_LEFT);
        pane.setAlignment(buttons, Pos.BOTTOM_RIGHT);

        // StackPane'i CartItem bileşenimize ekleme
        getChildren().add(pane);
    }

    // Sepetteki ürünün miktarlarını değiştircek butonları oluşturan fonksiyon
    public Button createChangeAmountButton(FontAwesomeIcon i, int amount) {
        FontAwesomeIconView icon = new FontAwesomeIconView(i);
        icon.setSize("35");
        icon.getStyleClass().add("icon");

        Button button = new Button();
        button.setGraphic(icon);
        button.getStyleClass().add("restaurant-btn");
        button.setOnMouseClicked(event -> {
            CartItemModel item = new CartItemModel(model.food);
            Main.userModel.changeAmountCartItem(item, amount);
            Main.getCart();
        });

        return button;
    }
}
