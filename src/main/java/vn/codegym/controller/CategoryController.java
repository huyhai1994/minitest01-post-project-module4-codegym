package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.codegym.service.ICategoryService;
import vn.codegym.uri.CategoryRequestUri;
import vn.codegym.uri.CategoryViewUri;

@Controller
@RequestMapping(value = CategoryRequestUri.BLANK)
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = CategoryRequestUri.BLANK)
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return CategoryViewUri.CATEGORY_INDEX;
    }

}
