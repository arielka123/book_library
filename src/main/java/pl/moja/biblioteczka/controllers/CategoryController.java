package pl.moja.biblioteczka.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
//import pl.moja.biblioteczka.modelFx.CategoryFx;
//import pl.moja.biblioteczka.modelFx.CategoryModel;
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
    public void initialize() {
        this.categoryModel = new CategoryModel();
    }


    public void addCategoryOnAction() throws ApplicationException {
//        System.out.println(categoryModel);
        categoryModel.saveCategoryInDataBase(categoryTextField.getText());
    }
}
