package ru.sbt.recipes.mvc.service;

import ru.sbt.recipes.mvc.entity.Recipe;

import java.util.List;

public interface RecipeDao {
    Recipe create(Recipe recipe);

    void delete(Long id);

    Recipe edit(Long idSource, Recipe values);

    List<Recipe> getRecipeByPartOfName(String name);

    Recipe getRecipe(Long id);

    List<Recipe> getAll();

    Recipe update(Recipe recipe);
}
