package vn.codegym.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import vn.codegym.model.category.Category;
import vn.codegym.service.ICategoryService;

import java.util.Locale;
import java.util.Optional;

public class CategoryFormatter implements Formatter<Category> {
    private final ICategoryService categoryService;


    @Autowired
    public CategoryFormatter(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category parse(String text, Locale locale) {
        Optional<Category> categoryOptional = categoryService.findById(Long.parseLong(text));
        return categoryOptional.orElse(null);
    }

    @Override
    public String print(Category object, Locale locale) {
        return "[" +object.getId() + ", " + object.getName() + "]";
    }
}
