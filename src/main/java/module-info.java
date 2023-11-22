module pl.moja.biblioteczka {
    requires javafx.controls;
    requires javafx.graphics;
    requires  javafx.fxml;

    opens pl.moja.biblioteczka to javafx.fxml;
    opens pl.moja.biblioteczka.controllers to javafx.fxml;

    exports pl.moja.biblioteczka;
    exports pl.moja.biblioteczka.controllers;

}