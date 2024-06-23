package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.codegym.model.category.Category;
import vn.codegym.service.ICategoryService;
import vn.codegym.uri.CategoryRequestUri;
import vn.codegym.uri.CategoryViewUri;

import java.util.Optional;

@Controller
@RequestMapping(CategoryRequestUri.CATEGORY)
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public String showCategories(Model model, Pageable pageable) {
        model.addAttribute("categories", categoryService.findAll(pageable));
        return CategoryViewUri.CATEGORY_INDEX;
    }

    @GetMapping(CategoryRequestUri.CREATE)
    public ModelAndView showCreatePage(ModelAndView modelAndView) {
        modelAndView.addObject("category", new Category());
        modelAndView.setViewName(CategoryViewUri.CATEGORY_CREATE);
        return modelAndView;
    }

    @PostMapping(CategoryRequestUri.CREATE)
    public String create(@ModelAttribute("category") Category category,  RedirectAttributes redirectAttributes) {
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Danh Muc Moi Da Tao Thanh Cong!!!");
        return CategoryViewUri.REDIRECT_TO_CATEGORY;
    }

    @GetMapping("/{id}/update")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Category> category= categoryService.findById(id);
        if (category.isPresent()) {
            ModelAndView modelAndView = new ModelAndView(CategoryViewUri.CATEGORY_UPDATE);
            modelAndView.addObject("category",category.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }



}
