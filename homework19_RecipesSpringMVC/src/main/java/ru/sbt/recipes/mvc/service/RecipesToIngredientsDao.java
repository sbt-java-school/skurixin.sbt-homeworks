package ru.sbt.recipes.mvc.service;

import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.IngredientProperty;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.entity.RecipesToIngredients;

import java.util.List;
import java.util.Map;

public interface RecipesToIngredientsDao {
    boolean addIngredientToRecipe(Recipe recipe, IngredientProperty ingredientProperty);

    Map<Recipe, List<IngredientProperty>> getCookingBook();

    RecipesToIngredients updateCount(Recipe recipe, Ingredient ingredient, Long i_count);

    boolean deleteIngredientFromRecipe(Recipe recipe, Ingredient ingredient);

    List<IngredientProperty> getIngredientsForRecipe(Recipe recipe);

    List<IngredientProperty> getIngredientsForRecipe(Long recipe_id);

    int deleteAllByRecipe(Long recipe_id);

    RecipesToIngredients getRecipeToIngredient(Long recipe_id, Long ingredient_id);
}
