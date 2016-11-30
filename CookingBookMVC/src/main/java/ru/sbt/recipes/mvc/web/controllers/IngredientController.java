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
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.service.IngredientDao;
import ru.sbt.recipes.mvc.service.RecipeDao;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import javax.servlet.http.HttpSession;

/**
 * Created by скурихин on 27.11.2016.
 */
@Controller
//@RequestMapping(value = "/recipe/{id}")
public class IngredientController {
    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    //GET запрос переименования рецепта
    @RequestMapping(value = "/recipes/ingredient/{id_ingredient}/rename")
    public String renameIngredientGET(Model model,
                                      @PathVariable("id_ingredient") Long id_ingredient) {
        Ingredient ingredient = ingredientDao.get(id_ingredient);
        model.addAttribute("ingredientFORM", ingredient);
        return "edit_ingredient";
    }

    //POST запрос переименования рецепта
    @RequestMapping(value = "/recipes/ingredient/{id_ingredient}/rename", method = RequestMethod.POST)
    public String renameIngredientPOST(Model model,
                                       @PathVariable("id_ingredient") Long id_ingredient,
                                       @ModelAttribute("ingredientFORM") Ingredient ingredient,
                                       BindingResult bindingResult,
                                       HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "edit_ingredient";
        }
        String newName = ingredient.getName();
        if (ingredientDao.getByName(newName) == null) {
            ingredientDao.edit(ingredient);
        } else {
            bindingResult.rejectValue("name", "error.ingredientFORM", "Данный ингредиент уже существует");
        }
        if (bindingResult.hasErrors()) {
            return "edit_ingredient";
        }
        Recipe recipeCurrent = (Recipe) httpSession.getAttribute("recipeCurrent");
        return "redirect:/recipes/" + recipeCurrent.getId();
    }
}
