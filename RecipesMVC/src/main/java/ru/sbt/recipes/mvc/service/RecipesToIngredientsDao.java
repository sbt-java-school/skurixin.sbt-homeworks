package ru.sbt.recipes.mvc.service;

import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.IngredientProperty;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.entity.RecipesToIngredients;

import java.util.List;
import java.util.Map;

public interface RecipesToIngredientsDao {
    void addIngredientToRecipe(Recipe recipe, IngredientProperty ingredientProperty);

    Map<Recipe, List<IngredientProperty>> getCookingBook();

    RecipesToIngredients updateCount(Recipe recipe, Ingredient ingredient, Long i_count);

    boolean deleteIngredientFromRecipe(Recipe recipe, Ingredient ingredient);

    List<IngredientProperty> getIngredientsForRecipe(Recipe recipe);

    List<IngredientProperty> getIngredientsForRecipe(long recipe_id);
}
