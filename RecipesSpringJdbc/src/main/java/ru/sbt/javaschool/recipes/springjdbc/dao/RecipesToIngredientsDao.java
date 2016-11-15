package ru.sbt.javaschool.recipes.springjdbc.dao;

import ru.sbt.javaschool.recipes.springjdbc.entity.Ingredient;
import ru.sbt.javaschool.recipes.springjdbc.entity.IngredientProperty;
import ru.sbt.javaschool.recipes.springjdbc.entity.Recipe;
import ru.sbt.javaschool.recipes.springjdbc.entity.RecipesToIngredients;

import java.util.List;
import java.util.Map;

/**
 * Created by скурихин on 14.11.2016.
 */
public interface RecipesToIngredientsDao {
    void addIngredientToRecipe(Recipe recipe, IngredientProperty ingredientProperty);

    Map<Recipe, List<IngredientProperty>> getCookingBook();

    RecipesToIngredients updateCount(Recipe recipe, Ingredient ingredient, Long i_count);

    boolean deleteIngredientFromRecipe(Recipe recipe, Ingredient ingredient);

    List<IngredientProperty> getIngredientsForRecipe(Recipe recipe);
}
