package vn.codegym.service;

import org.springframework.stereotype.Service;
import vn.codegym.model.DTO.ICountPosts;
import vn.codegym.model.category.Category;

public interface ICategoryService extends IGenerateService<Category> {
    void deleteCategoryById(Long id);
    Iterable<ICountPosts> getCountPosts();
}
