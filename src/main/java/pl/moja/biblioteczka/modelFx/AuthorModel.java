package pl.moja.biblioteczka.modelFx;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.biblioteczka.database.dao.AuthorDao;
import pl.moja.biblioteczka.database.dao.BookDao;
import pl.moja.biblioteczka.database.dbuitls.DbManager;
import pl.moja.biblioteczka.database.models.Author;
import pl.moja.biblioteczka.database.models.Book;
import pl.moja.biblioteczka.utils.converters.ConverterAuthor;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;

public class AuthorModel {
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>(new AuthorFx());

    public void saveAuthorInDataBase() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        Author author = ConverterAuthor.convertAuthorFxToAuthor(this.getAuthorFxObjectProperty());
        authorDao.creatOrUpdate(author);
        DbManager.closeConnectionSource();
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public void setAuthorFxObjectProperty(AuthorFx authorFxObjectProperty) {
        this.authorFxObjectProperty.set(authorFxObjectProperty);
    }
}
