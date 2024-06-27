package com.example.cuisine.controller;

import com.example.cuisine.model.Category;
import com.example.cuisine.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String displayCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategoryList());
        return "categories";
    }

    @GetMapping("/category/{id}")
    public String displayCategorieById(@PathVariable(name = "id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category";
    }

    @GetMapping("/form-category/{id}")
    public String displayFormCategory(@PathVariable(name = "id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "form-category";
    }

    @PostMapping("/form-category/{id}")
    public String updateCategory(@PathVariable("id") Long id, Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/categories";
    }

    @GetMapping("/form-category")
    public String createCategory(Model model) {
        model.addAttribute("category", new Category());
        return "form-category";
    }

    @PostMapping("/form-category")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        System.out.println(category);
        System.out.println(category.getId());
        if (result.hasErrors()) {
            return "form-category";
        } else {
            if(category.getId() != null) {
                categoryService.updateCategory(category.getId(), category);
                System.out.println(category.getId());
            } else {
                categoryService.createCategory(category);
                System.out.println("create category" + category.getId());
            }
        }
        return "redirect:/categories";
    }

}
