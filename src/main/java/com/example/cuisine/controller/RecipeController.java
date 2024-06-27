package com.example.cuisine.controller;

import com.example.cuisine.model.Recipe;
import com.example.cuisine.service.CategoryService;
import com.example.cuisine.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    @Autowired
    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("recipes", recipeService.getRecipeList());
        model.addAttribute("categories", categoryService.getCategoryList());
        return "index";
    }

    @GetMapping("/form-recipe")
    public String displayForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryService.getCategoryList());
        return "form-recipe";
    }

    @PostMapping("/form-recipe")
    public String createOrUpdateRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult result) {
        if (result.hasErrors()) {
            return "form-recipe";
        } else {
            if (recipe.getId() != null) {
                recipeService.updateRecipe(recipe.getId(), recipe);
            } else {
                recipeService.createRecipe(recipe);
            }
            return "redirect:/";
        }
    }

    @GetMapping("/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable(name = "id") Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/";
    }

    @GetMapping("/recipe/{id}")
    public String displayRecipe(@PathVariable(name = "id") Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryService.getCategoryList());
        return "recipe";
    }

    @GetMapping("/recipe/update/{id}")
    public String updateRecipe(@PathVariable("id") Long id, Model model){
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", categoryService.getCategoryList());
        return "form-recipe";
    }

}
