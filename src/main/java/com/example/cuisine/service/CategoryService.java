package com.example.cuisine.service;

import com.example.cuisine.model.Category;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class CategoryService {

    private List<Category> categoryList = new ArrayList<>();
    private Long currentId = 4L;

    boolean categorie1 = categoryList.add(new Category(1L, "Entrée", "Les entrées sont des plats qui sont servis avant le plat principal."));
    boolean categorie2 = categoryList.add(new Category(2L, "Plat", "Les plats sont des plats principaux."));
    boolean categorie3 = categoryList.add(new Category(3L, "Dessert", "Les desserts sont des plats sucrés servis après le plat principal."));

    public Category createCategory(Category category) {
        category.setId(currentId++);
        System.out.println(category.getId());
        categoryList.add(category);
        return category;
    }

    public Category getCategoryById(long id) {
        return categoryList.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La catégorie avec l'id " + id + " n'existe pas"));
    }

    public Category updateCategory(long id, Category category) {
        Category categoryToUpdate = getCategoryById(id);
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        return categoryToUpdate;
    }

    public void deleteCategory(long id) {
        categoryList.removeIf(c -> c.getId() == id);
    }

    public List<Category> getCategoriesByName(String name) {
        return categoryList.stream()
                .filter(c -> c.getName().equals(name))
                .toList();
    }

    public List<Category> getCategoriesByDescription(String description) {
        return categoryList.stream()
                .filter(c -> c.getDescription().equals(description))
                .toList();
    }
}
