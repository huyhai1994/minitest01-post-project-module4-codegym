package vn.codegym.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codegym.model.category.Category;
import vn.codegym.service.ICategoryService;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class RestCategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = iCategoryService.findById(id);
        if (!categoryOptional.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        iCategoryService.deleteCategoryById(id);
        return new ResponseEntity<>(categoryOptional.get(), HttpStatus.OK);
    }
}
