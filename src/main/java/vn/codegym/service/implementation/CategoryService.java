package vn.codegym.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.codegym.model.category.Category;
import vn.codegym.repository.ICategoryRepository;
import vn.codegym.service.ICategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Override
    public List<Category> findAll(Pageable pageable) {
        return iCategoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        iCategoryRepository.save(category);
    }

    @Override
    public Optional<Category>findById(Long id) {
        return iCategoryRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iCategoryRepository.deleteById(id);
    }
}
