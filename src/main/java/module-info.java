module com.gazi.acsepeti.acsepeti {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gazi.acsepeti.acsepeti to javafx.fxml;
    exports com.gazi.acsepeti.acsepeti;
}