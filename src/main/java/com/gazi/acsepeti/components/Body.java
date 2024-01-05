package com.gazi.acsepeti.components;

import com.gazi.acsepeti.interfaces.IGeneralComponentsFunctions;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**@see Body sýnýfýný
 * @see ScrollPane sýnýfýndan kalýttýk çünkü ScrollPane sýnýfý içine componentlar eklendiðinde
 * ve ekranda taþma meydana gelince otomatik olarak scrollbar ekliyor
 * @see IGeneralComponentsFunctions interface inden implement ettik
 * */
public class Body extends ScrollPane implements IGeneralComponentsFunctions {
    private GridPane gridPane;
    private final int maxHorizontalComponentCount;
    private int positionX = 0;
    private int positionY = 0;

    /**
     * @param components içeriðinde gösterceði componentlarý tutan array
     *                   (Bu sýnýf için restoran tutuyor)
     * @param maxHorizontalComponentCount yan yana en fazla kaç component olabilceðini belirtiyor
     * @param positionX componentlarý dizerken yatay eksende kaçýncý sýrada olduðumuzu belirtiyor
     * @param positionY componentlarý dizerken dikey eksende kaçýncý sýrada olduðumuzu belirtiyor
     */
    public Body(ArrayList<Parent> components, int maxHorizontalComponentCount) {
        this.maxHorizontalComponentCount = maxHorizontalComponentCount;
        this.gridPane = new GridPane();
        addComponents(components);
        setThis();
    }

    // Kendini ayarlama
    @Override
    public void setThis() {
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

    // Components kendi içinde dizme (Bu sýnýf için restoranlar)
    private void addComponents(ArrayList<Parent> components) {
        components.forEach(component -> {
            // Her bir componentin pozisyonunu ayarlayýp yerleþtirme
            gridPane.add(component, positionX, positionY);
            positionX++;
            if (positionX == maxHorizontalComponentCount) {
                positionX = 0;
                positionY++;
            }
        });
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
