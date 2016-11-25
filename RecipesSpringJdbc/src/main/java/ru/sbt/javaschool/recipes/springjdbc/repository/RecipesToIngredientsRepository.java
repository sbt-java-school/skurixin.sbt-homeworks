package ru.sbt.javaschool.recipes.springjdbc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.javaschool.recipes.springjdbc.entity.Ingredient;
import ru.sbt.javaschool.recipes.springjdbc.entity.Recipe;
import ru.sbt.javaschool.recipes.springjdbc.entity.RecipesToIngredients;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
public interface RecipesToIngredientsRepository extends JpaRepository<RecipesToIngredients, Long> {

    List<RecipesToIngredients> findByRecipe(Recipe recipe);

    RecipesToIngredients findByRecipeAndIngredient(Recipe recipe, Ingredient ingredient);
}
