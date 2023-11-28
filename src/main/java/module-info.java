module pl.moja.biblioteczka {
    requires javafx.controls;
    requires javafx.graphics;
    requires  javafx.fxml;
    requires java.sql;
    requires ormlite.jdbc;

    opens pl.moja.biblioteczka to javafx.fxml;
    opens pl.moja.biblioteczka.controllers to javafx.fxml;

    exports pl.moja.biblioteczka;
    exports pl.moja.biblioteczka.controllers;
}