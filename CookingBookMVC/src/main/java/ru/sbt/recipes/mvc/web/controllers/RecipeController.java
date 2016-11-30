package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.entity.RecipesToIngredients;
import ru.sbt.recipes.mvc.service.IngredientDao;
import ru.sbt.recipes.mvc.service.RecipeDao;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by скурихин on 27.11.2016.
 */
@Controller
@RequestMapping(value = "/recipes")
public class RecipeController {
    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    //создание рецепта
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showFormAddRecipe(Model model) {
        Recipe recipe = new Recipe();
        recipe.setName("defaultName");
        recipe.setDescription("defaultDescription");

        model.addAttribute("recipeFORM", recipe);
        return "create_edit_recipe";
    }

    //переименовать рецепт
    @RequestMapping(value = "/{id}/edit")
    public String editRecipe(@PathVariable("id") Long recipe_id,
                             Model model,
                             HttpSession httpSession) {
        Recipe recipe = recipeDao.getRecipe(recipe_id);
        model.addAttribute("recipeFORM", recipe);
        return "create_edit_recipe";
    }

    //POST запрос, обработка добавления или изменения рецепта
    @RequestMapping(value="/edit",method = RequestMethod.POST)
    public String createRecipeFromForm(@ModelAttribute("recipeFORM") @Valid Recipe recipe,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create_edit_recipe";
        }
        int result = 0;
        if (recipe.getId() != null) {
            result = recipeDao.updateIfNotExist(recipe);
        } else {
            result = recipeDao.createIfNotExist(recipe);
        }
        if (result == 0) {
            bindingResult.rejectValue("name", "error.recipeFORM", "Рецепт с таким именем уже есть!");
        }
        if (bindingResult.hasErrors()) {
            return "create_edit_recipe";
        }
        return "redirect:/recipes";
    }

    //удалить рецепт
    @RequestMapping(value = "/{id}/delete")
    public String deleteRecipe(@PathVariable("id") Long recipe_id) {
        recipesToIngredientsDao.deleteAllByRecipe(recipe_id);
        recipeDao.delete(recipe_id);
        return "redirect:/recipes";
    }

    /*
        подготовка вывода страницы рецепта,
        добавление в модель его ингредиентов
        и ингредиентов, которые могут быть добавлены(для выпадающего списка, в качестве подсказки пользователю)
     */
    @RequestMapping(value = "/{id}")
    public String ingredientsForRecipe(Model model,
                                       @PathVariable("id") Long recipe_id,
                                       HttpSession httpSession) {
        Recipe recipe = recipeDao.getRecipe(recipe_id);
        RecipesToIngredients relation = new RecipesToIngredients(recipe);
        model.addAttribute("recipeCurrent", recipe);
        model.addAttribute("recipeToIngredient", relation);

        MainController.prepareFormFields(model, recipe_id, recipesToIngredientsDao, ingredientDao);

        httpSession.setAttribute("recipeCurrent", recipe);
        return "recipe";
    }
}
