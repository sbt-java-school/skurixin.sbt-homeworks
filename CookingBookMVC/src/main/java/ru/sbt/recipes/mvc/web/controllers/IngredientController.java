package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
//@RequestMapping(value = "/recipe/{id}")
public class IngredientController {
    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;


}
