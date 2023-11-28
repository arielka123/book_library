package pl.moja.biblioteczka.utils.converters;

import pl.moja.biblioteczka.database.models.Category;
import pl.moja.biblioteczka.modelFx.CategoryFx;

public class ConverterCategory {
    public static CategoryFx convertToCategoryFx(Category category){
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName());
        return categoryFx;
    }

}
