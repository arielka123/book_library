package pl.moja.biblioteczka.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.BookFx;
import pl.moja.biblioteczka.modelFx.CategoryFx;
import pl.moja.biblioteczka.modelFx.ListBookModel;
import pl.moja.biblioteczka.utils.DialogsUtils;
import pl.moja.biblioteczka.utils.FxmlUtils;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;
import java.time.LocalDate;
import java.util.Objects;

public class ListBooksController {
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private ComboBox<AuthorFx> authorComboBox;
    @FXML
    private TableView<BookFx> booksTableView;
    @FXML
    private TableColumn<BookFx,String> titleColumn;
    @FXML
    private TableColumn<BookFx,String> descColumn;
    @FXML
    private TableColumn<BookFx, AuthorFx> authorColumn;
    @FXML
    private TableColumn<BookFx, CategoryFx> categoryColumn;
    @FXML
    private TableColumn<BookFx,Number> ratingColumn;
    @FXML
    private TableColumn<BookFx,String> isbnColumn;
    @FXML
    private TableColumn<BookFx, LocalDate> releaseColumn;
    @FXML
    private TableColumn<BookFx,BookFx> deleteColumn;
    @FXML
    private TableColumn<BookFx,BookFx> editColumn;

    private ListBookModel listBookModel;

    @FXML
    public void initialize() {
        this.listBookModel = new ListBookModel();
        try {
            this.listBookModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }

        this.categoryComboBox.setItems(listBookModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(listBookModel.getAuthorFxObservableList());
        this.listBookModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
        this.listBookModel.authorFxObjectPropertyProperty().bind(this.authorComboBox.valueProperty());

        this.booksTableView.setItems(this.listBookModel.getBookFxObservableList());
        this.titleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());
        this.authorColumn.setCellValueFactory(cellData->cellData.getValue().authorFxProperty());
        this.isbnColumn.setCellValueFactory(cellData->cellData.getValue().isbnProperty());
        this.categoryColumn.setCellValueFactory(cellData->cellData.getValue().categoryFxProperty());
        this.descColumn.setCellValueFactory(cellData->cellData.getValue().descriptionProperty());
        this.ratingColumn.setCellValueFactory(cellData->cellData.getValue().ratingProperty());
        this.releaseColumn.setCellValueFactory(cellData->cellData.getValue().releaseDateProperty());
        this.deleteColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue()) );
        this.editColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue()) );

        this.deleteColumn.setCellFactory(param -> new TableCell<>() {
            Button button = createButton("/icons/trash.png");

            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setGraphic(button);
                    setAlignment(Pos.CENTER);
                    button.setOnAction(event -> {
                        try {
                            listBookModel.deleteBook(item);
                        } catch (ApplicationException e) {
                            DialogsUtils.errorDialog(e.getMessage());
                        }
                    });
                } else {
                    setGraphic(null);
                }
            }
        });

        this.editColumn.setCellFactory(param-> new TableCell<>(){
            Button button = createButton("/icons/edit.png");
            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setGraphic(button);
                    setAlignment(Pos.CENTER);

                    button.setOnAction(event -> {
                        FXMLLoader loader = FxmlUtils.getLoader("/fxml/EditBook.fxml");
                        Scene scene = null;
                         try {
                             scene = new Scene(loader.load());
                        } catch (Exception e) {
                             DialogsUtils.errorDialog(e.getMessage());
                             throw new RuntimeException("error",e);
                         }

                        BookController controller = loader.getController();
                        controller.getBookModel().setBookFxObjectProperty(item);
                        controller.bindings();

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle(FxmlUtils.getResourceBundle().getString("edit"));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    });
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private Button createButton(String path){
        Button button = new Button();
        Image image = new Image(Objects.requireNonNull(this.getClass().getResource(path)).toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        return button;
    }

    public void filterOnActionComboBox() {
        this.listBookModel.filterBooksList();
    }

    public void clearAuthorComboBox() {
        this.authorComboBox.getSelectionModel().clearSelection();
    }

    public void clearCategoryComboBox() {
        this.categoryComboBox.getSelectionModel().clearSelection();
    }
}
