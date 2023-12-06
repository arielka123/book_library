package pl.moja.biblioteczka.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.BookModel;
import pl.moja.biblioteczka.modelFx.CategoryFx;
import pl.moja.biblioteczka.utils.DialogsUtils;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

public class BookController {

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private ComboBox<AuthorFx> authorComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TextField isbnTextField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextField titleTextField;

    private BookModel bookModel;

    @FXML
    public void initialize(){
        this.bookModel = new BookModel();
        try {
            this.bookModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();
    }

    private void bindings() {
        this.categoryComboBox.setItems(this.bookModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(this.bookModel.getAuthorFxObservableList());

        this.bookModel.getBookFxObjectProperty().categoryFxProperty().bind(categoryComboBox.valueProperty());
        this.bookModel.getBookFxObjectProperty().authorFxProperty().bind(authorComboBox.valueProperty());
        this.bookModel.getBookFxObjectProperty().descriptionProperty().bind(descriptionTextArea.textProperty());
        this.bookModel.getBookFxObjectProperty().ratingProperty().bind(ratingSlider.valueProperty());
        this.bookModel.getBookFxObjectProperty().isbnProperty().bind(isbnTextField.textProperty());
        this.bookModel.getBookFxObjectProperty().releaseDateProperty().bind(releaseDatePicker.valueProperty());
        this.bookModel.getBookFxObjectProperty().titleProperty().bind(titleTextField.textProperty());
    }

    public void addBookOnAction() {
//        System.out.println(this.bookModel.getBookFxObjectProperty().toString());
        try {
            this.bookModel.saveBookInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        clearInputs();
    }

    private void clearInputs() {
        this.categoryComboBox.setItems(null);
        this.authorComboBox.setItems(null);
        this.isbnTextField.clear();
        this.ratingSlider.setValue(0);
        this.descriptionTextArea.clear();
        this.titleTextField.clear();
        this.releaseDatePicker.valueProperty().setValue(null);
    }
}
