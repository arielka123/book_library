package pl.moja.biblioteczka.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.AuthorModel;
import pl.moja.biblioteczka.utils.DialogsUtils;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

public class AuthorController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button addButton;
    @FXML
    private TableView<AuthorFx> authorTableView;
    @FXML
    private TableColumn<AuthorFx, String> nameColumn;
    @FXML
    private TableColumn<AuthorFx, String> surnameColumn;
    @FXML
    private MenuItem deleteMenuItem;

    private AuthorModel authorModel;

    public void initialize() {
        this.authorModel = new AuthorModel();
        try {
            this.authorModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();
        bindingsTableView();
    }

    private void bindingsTableView() {
        this.authorTableView.setItems(this.authorModel.getAuthorFxObservableList());
        this.nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cellData->cellData.getValue().surnameProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn()); //uaktywnia komórki w tableView
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.authorTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            this.authorModel.setAuthorFxObjectPropertyEdit(newValue);//przekazuje nasłuchiwaną, klikniętą wartość
        } );
    }

    private void bindings() {
        authorModel.authorFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        this.authorModel.authorFxObjectPropertyProperty().get().surnameProperty().bind(this.surnameTextField.textProperty()); // oszczedność. Automatycznie dane wpisane do texfilda znajdują się w obiekcie
        this.addButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.surnameTextField.textProperty().isEmpty()));

        this.deleteMenuItem.disableProperty().bind( this.authorTableView.getSelectionModel().selectedItemProperty().isNull());
    }

    public void OnActionAddAuthor() {
        System.out.println(this.authorModel.getAuthorFxObjectProperty().getName());
        System.out.println(this.authorModel.getAuthorFxObjectProperty().getSurname());
        try {
            this.authorModel.saveAuthorInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        this.nameTextField.clear();
        this.surnameTextField.clear();
    }

    public void OnEditCommitName(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        String newValue = authorFxStringCellEditEvent.getNewValue();
        this.authorModel.getAuthorFxObjectPropertyEdit().setName(newValue);
        updateInDatabase();
    }

    public void OnEditCommitSurname(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        String newValue = authorFxStringCellEditEvent.getNewValue();
        this.authorModel.getAuthorFxObjectPropertyEdit().setSurname(newValue);
        updateInDatabase();
    }
    private void updateInDatabase() {
        try {
            this.authorModel.saveAuthorEditInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }


    public void deleteAuthorOnAction() {
        try {
            authorModel.deleteAuthorInDatBase();
        } catch (ApplicationException e) {
           DialogsUtils.errorDialog(e.getMessage());
        }
    }
}
