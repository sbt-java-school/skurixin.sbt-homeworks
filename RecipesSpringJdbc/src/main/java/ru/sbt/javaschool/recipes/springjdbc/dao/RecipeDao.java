package ru.sbt.javaschool.recipes.springjdbc.dao;

import ru.sbt.javaschool.recipes.springjdbc.entity.Recipe;

import java.util.List;

/**
 * Created by скурихин on 14.11.2016.
 */
public interface RecipeDao {
    Recipe create(Recipe recipe);

    void delete(Long id);

    Recipe edit(Long idSource, Recipe values);

    List<Recipe> getRecipeByPartOfName(String name);

    Recipe getRecipe(Long id);

    List<Recipe> getAll();
}
