package pl.moja.biblioteczka.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.biblioteczka.database.dao.AuthorDao;
import pl.moja.biblioteczka.database.dao.BookDao;
import pl.moja.biblioteczka.database.dao.CategoryDao;
import pl.moja.biblioteczka.database.models.Author;
import pl.moja.biblioteczka.database.models.Book;
import pl.moja.biblioteczka.database.models.Category;
import pl.moja.biblioteczka.utils.converters.ConverterAuthor;
import pl.moja.biblioteczka.utils.converters.ConverterBook;
import pl.moja.biblioteczka.utils.converters.ConverterCategory;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListBookModel {

    private final  ObservableList<BookFx> bookFxObservableList = FXCollections.observableArrayList();
    private final ObservableList<CategoryFx>categoryFxObservableList = FXCollections.observableArrayList();
    private final ObservableList<AuthorFx> authorFxObservableList= FXCollections.observableArrayList();

    private final ObjectProperty<CategoryFx>categoryFxObjectProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<AuthorFx>authorFxObjectProperty = new SimpleObjectProperty<>();

    private final List<BookFx> bookFxList = new ArrayList<>();

    public void init() throws ApplicationException {
        BookDao bookDao = new BookDao();
        List<Book> bookList = bookDao.queryForAll(Book.class);
        bookFxList.clear();
        bookList.forEach(book -> this.bookFxList.add(ConverterBook.convertToBookFx(book)));

        this.bookFxObservableList.setAll(bookFxList);
        initAuthors();
        initCategory();
    }

    public void filterBooksList(){
        if(getAuthorFxObjectProperty()!=null && getCategoryFxObjectProperty()!=null){
            filterPredicate(predicateAuthor().and(predicateCategory()));
        }else if(getCategoryFxObjectProperty()!=null){
            filterPredicate(predicateCategory());
        } else if (getAuthorFxObjectProperty()!=null) {
            filterPredicate(predicateAuthor());
        }else{
            this.bookFxObservableList.setAll(bookFxList);
        }
    }

    private void initCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.queryForAll(Category.class);
        categories.forEach(category -> {
            this.categoryFxObservableList.add(ConverterCategory.convertToCategoryFx(category));
        });
    }

    private void initAuthors() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author>authors = authorDao.queryForAll(Author.class);
        authors.forEach(author -> {
            this.authorFxObservableList.add(ConverterAuthor.convertToAuthorFx(author));
        });
    }
    private Predicate<BookFx> predicateAuthor(){
        return bookFx -> bookFx.getAuthorFx().getId()==getAuthorFxObjectProperty().getId();
    }
    private Predicate<BookFx> predicateCategory(){
        return bookFx -> bookFx.getCategoryFx().getId() ==getCategoryFxObjectProperty().getId();
    }

    private void filterPredicate(Predicate<BookFx>predicate){
        List<BookFx>newList = bookFxList.stream().filter(predicate).collect(Collectors.toList()); //filtruje i tworzy now liste

        this.bookFxObservableList.setAll(newList);
    }

    public void deleteBook(BookFx bookFx) throws ApplicationException {
        BookDao bookDao = new BookDao();
        bookDao.deleteById(Book.class, bookFx.getId());
        init();
    }

    public ObservableList<BookFx> getBookFxObservableList() {
        return bookFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

}

