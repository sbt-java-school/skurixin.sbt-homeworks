package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.IngredientProperty;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.service.IngredientDao;
import ru.sbt.recipes.mvc.service.RecipeDao;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import java.util.List;

@Transactional
@Controller
public class MainController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(ModelMap model) {
        Recipe borstch = recipeDao.create(new Recipe("borstch"));
        Recipe vodka = recipeDao.create(new Recipe("vodka"));
        Recipe beaf = recipeDao.create(new Recipe("beaf"));
        Ingredient look = ingredientDao.create(new Ingredient("look"));
        Ingredient svekla = ingredientDao.create(new Ingredient("svekla"));
        Ingredient water = ingredientDao.create(new Ingredient("water"));
        Ingredient meat = ingredientDao.create(new Ingredient("meat"));
        recipesToIngredientsDao.addIngredientToRecipe(borstch, new IngredientProperty(look, 100L));
        recipesToIngredientsDao.addIngredientToRecipe(borstch, new IngredientProperty(svekla, 200L));
        recipesToIngredientsDao.addIngredientToRecipe(borstch, new IngredientProperty(water, 50L));
        recipesToIngredientsDao.addIngredientToRecipe(vodka, new IngredientProperty(water, 100L));
        recipesToIngredientsDao.addIngredientToRecipe(beaf, new IngredientProperty(meat, 200L));
        recipesToIngredientsDao.addIngredientToRecipe(beaf, new IngredientProperty(look, 50L));

        return "redirect:/recipes";
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public String index(ModelMap model) {
        Recipe borstch = recipeDao.create(new Recipe("borstch"));
        Recipe recipe = recipeDao.getRecipe(borstch.getId());
        List<Recipe> all = recipeDao.getAll();
        model.put("list", all);
        return "index";
    }


    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    public String createRecipeFromForm(@ModelAttribute("recipeFORM") Recipe recipe) {
        recipeDao.create(recipe);
        return "redirect:/recipes";
    }

    @RequestMapping(value = "/recipes/add", method = RequestMethod.GET)
    public String showFormAddRecipe(Model model) {
        Recipe recipe = new Recipe();
        recipe.setName("defaultName");
        recipe.setDescription("defaultDescription");

        model.addAttribute("recipeFORM", recipe);
        return "create_recipe";
    }

//    @RequestMapping(value = "/recipes/recipe/{id}", method = RequestMethod.GET)
//    public String pageIngredients(@PathVariable("id") long id,
//                                  ModelMap model) {
////        List<Recipe> all = recipeDao.getAll();
////        model.put("list", all);
//        return "ingredients";
//    }

}
