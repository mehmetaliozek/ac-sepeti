module com.gazi.acsepeti.acsepeti {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.json;


    opens com.gazi.acsepeti to javafx.fxml;
    exports com.gazi.acsepeti;
}