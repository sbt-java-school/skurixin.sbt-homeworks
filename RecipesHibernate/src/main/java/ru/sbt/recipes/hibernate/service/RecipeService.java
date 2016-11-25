package ru.sbt.recipes.hibernate.service;

import ru.sbt.recipes.hibernate.entity.Recipe;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    void delete(Recipe recipe);

    Recipe edit(Long idSource, Recipe values);

    List<Recipe> getRecipeByPartOfName(String name);

    Recipe getRecipe(Long id);

    List<Recipe> getAll();
}
