package com.example.cuisine.service;

import com.example.cuisine.model.Recipe;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Service
public class RecipeService {
    private List<Recipe> recipeList = new ArrayList<>();
    private Long id = 1L;

    public Recipe createRecipe(Recipe recipe) {
        recipe.setId(id++);
        recipeList.add(recipe);
        return recipe;
    }

    public Recipe getRecipeById(Long id) {
        return  recipeList.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La recette avec l'id " + id + " n'existe pas"));
    }

    public Recipe updateRecipe(Long id, Recipe recipe) {
        Recipe recipeToUpdate = getRecipeById(id);
        recipeToUpdate.setName(recipe.getName());
        recipeToUpdate.setInstructions(recipe.getInstructions());
        recipeToUpdate.setIngredients(recipe.getIngredients());
        recipeToUpdate.setCategoryId(recipe.getCategoryId());
        return recipeToUpdate;
    }

    public void deleteRecipe(Long id) {
        recipeList.removeIf(r -> Objects.equals(r.getId(), id));
    }

    public List<Recipe> getRecipesByCategorie(Long categoryId) {
        return recipeList.stream()
                .filter(r -> Objects.equals(r.getCategoryId(), categoryId))
                .toList();
    }
}
