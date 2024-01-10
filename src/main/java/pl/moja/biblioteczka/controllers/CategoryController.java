package pl.moja.biblioteczka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.moja.biblioteczka.modelFx.CategoryFx;
import pl.moja.biblioteczka.modelFx.CategoryModel;
import pl.moja.biblioteczka.utils.DialogsUtils;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

import java.sql.SQLException;

public class CategoryController {

    @FXML
    private Button addCategoryButton;
    @FXML
    private TextField categoryTextField;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private  Button deleteCategoryButton;
    @FXML
    private  Button editCategoryButton;
    @FXML
    private TreeView<String>categoryTreeView;

    private CategoryModel categoryModel;

    @FXML
    public void initialize() {
        this.categoryModel = new CategoryModel();
        try {
            this.categoryModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        this.categoryComboBox.setItems(this.categoryModel.getCategoryList());
        this.categoryTreeView.setRoot(this.categoryModel.getRoot());
        initBindings();
    }

    private void initBindings() {
        addCategoryButton.disableProperty().bind(categoryTextField.textProperty().isEmpty());   //gdy textfield jest pusty =true i przycisk wyłączony
        deleteCategoryButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());
        editCategoryButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());

    }

    public void OnActionAddCategory() {
     String  name = categoryTextField.getText();
        try {
            categoryModel.saveCategoryInDataBase(name);
            categoryTextField.clear();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public void OnActionDeleteCategory(){
        try {
            categoryModel.deleteCategoryById();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public void OnActionComboBox(){
        CategoryFx selectedComboBox = this.categoryComboBox.getSelectionModel().getSelectedItem();
        this.categoryModel.setCategory(selectedComboBox);

    }

    public void OnActionEditCategory(){
        String newCategoryName = DialogsUtils.editDialog(this.categoryModel.getCategory().getName());
        if(newCategoryName!=null){
            this.categoryModel.getCategory().setName(newCategoryName);
            try {
                this.categoryModel.updateCategoryInDatabase();
            } catch (ApplicationException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
        }
    }
}
