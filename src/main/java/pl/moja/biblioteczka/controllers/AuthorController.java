package pl.moja.biblioteczka.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
//import pl.moja.biblioteczka.modelFx.AuthorFx;
//import pl.moja.biblioteczka.modelFx.AuthorModel;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.AuthorModel;
import pl.moja.biblioteczka.utils.DialogsUtils;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

import java.sql.SQLException;

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
        authorModel.authorFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        this.authorModel.authorFxObjectPropertyProperty().get().surnameProperty().bind(this.surnameTextField.textProperty()); // oszczedność. Automatycznie dane wpisane do texfilda znajdują się w obiekcie
        this.addButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.surnameTextField.textProperty().isEmpty()));

        this.authorTableView.setItems(this.authorModel.getAuthorFxObservableList());
        this.nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cellData->cellData.getValue().surnameProperty());

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


}
