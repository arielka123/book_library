package pl.moja.biblioteczka.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import pl.moja.biblioteczka.controllers.CategoryController;
import pl.moja.biblioteczka.database.dao.CategoryDao;
import pl.moja.biblioteczka.database.dbuitls.DbManager;
import pl.moja.biblioteczka.database.models.Category;
import pl.moja.biblioteczka.utils.converters.ConverterCategory;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

import java.util.List;

public class CategoryModel {

    private ObservableList<CategoryFx> categoryList = FXCollections.observableArrayList();  //lista pobrana z bazy
    private ObjectProperty<CategoryFx> category = new SimpleObjectProperty<>(); //wybrany element z combobox
    private TreeItem<String>root = new TreeItem<>();

    public void init() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao(); //połączenie do DB
        List<Category> categories = categoryDao.queryForAll(Category.class); //wyciagamy wszystkie kategorie z bazy
        initCategoryList(categories);
        initRoot(categories);
        DbManager.closeConnectionSource();
    }

    private void initRoot(List<Category> categories) {
        root.getChildren().clear();
        categories.forEach(c->{
            TreeItem<String> categoryItem = new TreeItem<>(c.getName());
            c.getBooks().forEach(b->{
                categoryItem.getChildren().add(new TreeItem<>(b.getTitle()));
            });
            root.getChildren().add(categoryItem);
        });
    }

    private void initCategoryList(List<Category> categories) {
        this.categoryList.clear();
        categories.forEach(c ->{
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(c);
            this.categoryList.add(categoryFx);
    });
    }

    public void deleteCategoryById() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.deleteById(Category.class, category.getValue().getId());
        DbManager.closeConnectionSource();
        init();
    }
    public void saveCategoryInDataBase(String name) throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
        category.setName(name);
        categoryDao.creatOrUpdate(category);
        DbManager.closeConnectionSource();
        init();
    }

    public void updateCategoryInDatabase() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        Category tempCategory = categoryDao.findById(Category.class, getCategory().getId());
        tempCategory.setName(getCategory().getName());
        categoryDao.creatOrUpdate(tempCategory);

        DbManager.closeConnectionSource();
        init();
    }

    public ObservableList<CategoryFx> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ObservableList<CategoryFx> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryFx getCategory() {
        return category.get();
    }

    public ObjectProperty<CategoryFx> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryFx category) {
        this.category.set(category);
    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void setRoot(TreeItem<String> root) {
        this.root = root;
    }
}
