package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.entity.RecipesToIngredients;
import ru.sbt.recipes.mvc.service.RecipeDao;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import javax.servlet.http.HttpSession;

/**
 * Created by скурихин on 27.11.2016.
 */
@Controller
@RequestMapping(value = "/recipes/{recipe_id}/ingredient/{ingredient_id}")
public class RecipeController {
    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    @RequestMapping(value = "/delete")
    public String deleteIngredientFromRecipe(
            Model model,
            @PathVariable("recipe_id") Long recipe_id,
            @PathVariable("ingredient_id") Long ingredient_id) {
        boolean b = recipesToIngredientsDao.deleteIngredientFromRecipe(new Recipe(recipe_id), new Ingredient(ingredient_id));
        return "redirect:/recipes/"+recipe_id;
    }

    @RequestMapping(value="edit")
    public String editRecipesIngredientCount(
            Model model,
            @PathVariable("recipe_id") Long recipe_id,
            @PathVariable("ingredient_id") Long ingredient_id,
            HttpSession httpSession) {
        RecipesToIngredients recipeToIngredient = recipesToIngredientsDao.getRecipeToIngredient(recipe_id, ingredient_id);
        httpSession.setAttribute("recipeCurrent",recipeToIngredient.getRecipe());
        httpSession.setAttribute("ingredient",recipeToIngredient.getIngredient());

        model.addAttribute("recipeToIngredient",recipeToIngredient);
        return "edit_count";
    }
}
