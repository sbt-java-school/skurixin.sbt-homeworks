package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sbt.recipes.mvc.entity.IngredientProperty;
import ru.sbt.recipes.mvc.service.RecipeDao;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import java.util.List;

/**
 * Created by скурихин on 27.11.2016.
 */
@Controller
@RequestMapping(value = "/recipe/{id}")
public class RecipeController {
    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    @RequestMapping(value = "")
    public String ingredientsForRecipe(ModelMap model,
                                       @PathVariable("id") long recipe_id){
        List<IngredientProperty> list = recipesToIngredientsDao.getIngredientsForRecipe(recipe_id);
        model.put("ingredients",list);
        return "recipe";
    }

}
