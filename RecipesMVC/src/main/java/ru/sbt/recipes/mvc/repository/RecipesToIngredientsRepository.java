package ru.sbt.recipes.mvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.entity.RecipesToIngredients;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
public interface RecipesToIngredientsRepository extends JpaRepository<RecipesToIngredients, Long> {

    List<RecipesToIngredients> findByRecipe(Recipe recipe);

    RecipesToIngredients findByRecipeAndIngredient(Recipe recipe, Ingredient ingredient);
}
