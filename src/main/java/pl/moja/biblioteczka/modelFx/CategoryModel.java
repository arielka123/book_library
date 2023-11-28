package pl.moja.biblioteczka.modelFx;

import pl.moja.biblioteczka.database.dao.CategoryDao;
import pl.moja.biblioteczka.database.models.Category;
import pl.moja.biblioteczka.utils.exceptions.ApplicationException;

public class CategoryModel {

    public void saveCategoryInDataBase(String name) throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
//        categoryDao.creatOrUpdate(category);
//        DbManager.closeConnectionSource();
    }
}
