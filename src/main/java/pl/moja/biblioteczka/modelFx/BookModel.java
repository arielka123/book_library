package pl.moja.biblioteczka.modelFx;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.biblioteczka.database.dao.AuthorDao;
import pl.moja.biblioteczka.database.dao.CategoryDao;
import pl.moja.biblioteczka.database.dbuitls.DbManager;
import pl.moja.biblioteczka.database.models.Author;
import pl.moja.biblioteczka.database.models.Category;
import pl.moja.biblioteczka.utils.converters.ConverterAuthor;
import pl.moja.biblioteczka.utils.converters.ConverterCategory;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

import java.util.List;

public class BookModel {
    private final ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>(new BookFx());

    //aby działały comboBoxy
    private final ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();
    private final ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    public void init() throws ApplicationException {
        initAuthorList();
        initCategoryList();
    }

    private void initCategoryList() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(c->{
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(c);
            categoryFxObservableList.add(categoryFx);
        });
        DbManager.closeConnectionSource();
    }

    private void initAuthorList() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);

        authorFxObservableList.clear();
        authorList.forEach(c->{
            AuthorFx authorFx = ConverterAuthor.convertToAuthorFx(c);
            authorFxObservableList.add(authorFx);
        });
        DbManager.closeConnectionSource();
    }

    public BookFx getBookFxObjectProperty() {
        return bookFxObjectProperty.get();
    }

//    public ObjectProperty<BookFx> bookFxObjectPropertyProperty() {
//        return bookFxObjectProperty;
//    }

//    public void setBookFxObjectProperty(BookFx bookFxObjectProperty) {
//        this.bookFxObjectProperty.set(bookFxObjectProperty);
//    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }
}
