module pl.moja.biblioteczka.Main {
    requires javafx.controls;
    requires javafx.graphics;
    requires  javafx.fxml;
    requires java.sql;
    requires ormlite.jdbc;

    opens pl.moja.biblioteczka to javafx.fxml, ormlite.jdbc;
    opens pl.moja.biblioteczka.controllers to javafx.fxml;
    opens pl.moja.biblioteczka.database.models to ormlite.jdbc;

    exports pl.moja.biblioteczka;
    exports pl.moja.biblioteczka.modelFx;
    exports pl.moja.biblioteczka.controllers;
    exports pl.moja.biblioteczka.database.models;
    exports pl.moja.biblioteczka.utils.exceptions;
}