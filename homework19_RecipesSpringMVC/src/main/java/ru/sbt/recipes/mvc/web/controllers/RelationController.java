package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.IngredientProperty;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.entity.RecipesToIngredients;
import ru.sbt.recipes.mvc.service.IngredientDao;
import ru.sbt.recipes.mvc.service.RecipeDao;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by скурихин on 30.11.2016.
 */
@Controller
public class RelationController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    //удаление связи рецепта и ингредиента
    @RequestMapping(value = "/recipes/{recipe_id}/ingredient/{ingredient_id}/delete")
    public String deleteIngredientFromRecipe(
            Model model,
            @PathVariable("recipe_id") Long recipe_id,
            @PathVariable("ingredient_id") Long ingredient_id) {
        boolean b = recipesToIngredientsDao.deleteIngredientFromRecipe(new Recipe(recipe_id), new Ingredient(ingredient_id));
        return "redirect:/recipes/" + recipe_id;
    }

    //изменение количества ингредиента в рецепте
    @RequestMapping(value = "/recipes/{recipe_id}/ingredient/{ingredient_id}/edit")
    public String editRecipesIngredientCount(
            Model model,
            @PathVariable("recipe_id") Long recipe_id,
            @PathVariable("ingredient_id") Long ingredient_id,
            HttpSession httpSession) {
        RecipesToIngredients recipeToIngredient = recipesToIngredientsDao.getRecipeToIngredient(recipe_id, ingredient_id);
        httpSession.setAttribute("recipeCurrent", recipeToIngredient.getRecipe());
        httpSession.setAttribute("ingredient", recipeToIngredient.getIngredient());

        model.addAttribute("recipeToIngredient", recipeToIngredient);
        return "edit_count";
    }

    //добавление ингредиента в рецепт
    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.POST)
    public String saveRecipeToIngredient(Model model,
                                         @ModelAttribute("recipeToIngredient") @Valid RecipesToIngredients recipeToIngredient,
                                         HttpSession httpSession,
                                         BindingResult bindingResult) {
        Recipe recipe = (Recipe) httpSession.getAttribute("recipeCurrent");
        if (recipeToIngredient.getCount() <= 0) {
            MainController.prepareFormFields(model, recipe.getId(), recipesToIngredientsDao, ingredientDao);
            recipeToIngredient.setIngredient(null);
            recipeToIngredient.setCount(1L);
            bindingResult.rejectValue("count", "error.recipeToIngredient", "Колличество должно быть больше 0");
            return "recipe";
        }
        if ("".equals(recipeToIngredient.getIngredient().getName())) {
            MainController.prepareFormFields(model, recipe.getId(), recipesToIngredientsDao, ingredientDao);
            recipeToIngredient.setIngredient(null);
            bindingResult.rejectValue("ingredient", "error.recipeToIngredient", "Введите название ингредиента");
            return "recipe";
        }
        if (bindingResult.hasErrors()) {
            return "recipe";
        }

        Ingredient ingredient;
        if (recipeToIngredient.getId() != null) {
            ingredient = (Ingredient) httpSession.getAttribute("ingredient");
            RecipesToIngredients resultUpdate = recipesToIngredientsDao.updateCount(recipe, ingredient, recipeToIngredient.getCount());
        } else {
            ingredient = ingredientDao.getByName(recipeToIngredient.getIngredient().getName());
            if (ingredient == null) {
                ingredient = ingredientDao.create(recipeToIngredient.getIngredient());
            }

            boolean resultUpdate = recipesToIngredientsDao.addIngredientToRecipe(recipe,
                    new IngredientProperty(ingredient, recipeToIngredient.getCount()));
            if (!resultUpdate) {
                MainController.prepareFormFields(model, recipe.getId(), recipesToIngredientsDao, ingredientDao);
                recipeToIngredient.setIngredient(null);
                bindingResult.rejectValue("ingredient", "error.recipeToIngredient", "Данный ингредиент уже добавлен в рецепт");
            }
        }
        if (bindingResult.hasErrors()) {
            return "recipe";
        }
        return "redirect:/recipes/" + recipe.getId();
    }


}
