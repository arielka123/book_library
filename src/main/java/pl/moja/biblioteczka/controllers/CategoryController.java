package pl.moja.biblioteczka.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.moja.biblioteczka.modelFx.CategoryFx;
import pl.moja.biblioteczka.modelFx.CategoryModel;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

import java.sql.SQLOutput;

public class CategoryController {

    @FXML
    private Button addCategoryButton;
    @FXML
    private TextField categoryTextField;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    private CategoryModel categoryModel;
    @FXML
    private  Button deleteCategoryButton;

    @FXML
    public void initialize() throws ApplicationException {
        this.categoryModel = new CategoryModel();
        this.categoryModel.init();
        this.categoryComboBox.setItems(this.categoryModel.getCategoryList());
        initBindings();
    }

    private void initBindings() {
        addCategoryButton.disableProperty().bind(categoryTextField.textProperty().isEmpty());   //gdy textfield jest pusty =true i przycisk wyłączony

        deleteCategoryButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());

    }

    public void addCategoryOnAction() {
     String  name = categoryTextField.getText();
        try {
            categoryModel.saveCategoryInDataBase(name);
            categoryTextField.clear();
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
            //TODO alert z bundles
        }
    }

    public void deleteCategoryOnAction() throws ApplicationException {
        categoryModel.deleteCategoryById();
    }

    public void comboBoxOnAction(){
        CategoryFx selectedComboBox = this.categoryComboBox.getSelectionModel().getSelectedItem();

        this.categoryModel.setCategory(selectedComboBox);

    }
}
