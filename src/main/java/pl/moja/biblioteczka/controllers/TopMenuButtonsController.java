package pl.moja.biblioteczka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
public class TopMenuButtonsController {

    public static final String ADD_BOOK_FXML = "/fxml/AddBook.fxml";
    public static final String ADD_CATEGORY_FXML = "/fxml/AddCategory.fxml";
    public static final String STATS_FXML = "/fxml/Stats.fxml";
    public static final String LIST_BOOKS_FXML = "/fxml/ListBooks.fxml";
    public static final String LIBRARY_FXML = "/fxml/Library.fxml";
    public static final String ADD_AUTHOR_FXML = "/fxml/AddAuthor.fxml";

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

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

    @FXML
    public void addBook() {
        resetToggleButtons();
        mainController.setCenter(ADD_BOOK_FXML);
    }

    public void addCategory() {
        resetToggleButtons();

        mainController.setCenter(ADD_CATEGORY_FXML);
    }

    public void addAuthor() {
        resetToggleButtons();
        mainController.setCenter(ADD_AUTHOR_FXML);
    }

    private void resetToggleButtons() {
        if (toggleButtons.getSelectedToggle() != null) {
            toggleButtons.getSelectedToggle().setSelected(false);
        }
    }
}
