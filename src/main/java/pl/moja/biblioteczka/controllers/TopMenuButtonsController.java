package pl.moja.biblioteczka.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

import java.util.ResourceBundle;

public class TopMenuButtonsController {

    public static final String ADD_BOOK_FXML = "/fxml/AddBook.fxml";
    public static final String ADD_CATEGORY_FXML = "/fxml/AddCategory.fxml";
    public static final String STATS_FXML = "/fxml/Stats.fxml";
    public static final String LIST_BOOKS_FXML = "/fxml/ListBooks.fxml";
    public static final String LIBRARY_FXML = "/fxml/Library.fxml";

    private MainController mainController;

    @FXML
    private ToggleGroup toggleButtons;

    @FXML
    public void openLibrary() {
        mainController.setCenter(LIBRARY_FXML);

    }

    @FXML
    public void openListBooks() {
        mainController.setCenter(LIST_BOOKS_FXML);

    }

    @FXML
    public void openStatistics() {
        mainController.setCenter(STATS_FXML);

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void addBook() {
        resetToggleButtons();
        mainController.setCenter(ADD_BOOK_FXML);
    }

    public void addCategory() {
        resetToggleButtons();

        mainController.setCenter(ADD_CATEGORY_FXML);
    }

    private void resetToggleButtons() {
        if (toggleButtons.getSelectedToggle() != null) {
            toggleButtons.getSelectedToggle().setSelected(false);
        }
    }
}
