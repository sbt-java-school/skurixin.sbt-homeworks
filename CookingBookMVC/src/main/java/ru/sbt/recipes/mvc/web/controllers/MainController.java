package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.IngredientProperty;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.service.IngredientDao;
import ru.sbt.recipes.mvc.service.RecipeDao;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Controller
@RequestMapping(value = "/recipes")
public class MainController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    //start page
    //отображение рецептов
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<Recipe> all = recipeDao.getAll();
        model.put("list", all);
        if(all.size()==0){
            model.addAttribute("searchMessage", "Рецепты не найдены");
        }
        else {
            model.addAttribute("searchMessage", "Рецепты:");
        }
        return "index";
    }

    //поиск рецептов по части имени
    @RequestMapping(method = RequestMethod.POST, params = "recipeName")
    public String showRecipesByName(@RequestParam("recipeName") String recipeName,
                                    ModelMap model) {
        List<Recipe> recipeByPartOfName = recipeDao.getRecipeByPartOfName(recipeName);
        model.put("list", recipeByPartOfName);
        if(recipeByPartOfName.size()==0){
            model.addAttribute("searchMessage", "Рецепты не найдены");
        }
        else {
            model.addAttribute("searchMessage", "Найдено рецептов: " + recipeByPartOfName.size());
        }
        return "index";
    }

    /*
    метод, добавляющий в модель model ингредиенты, которые уже добавленные в рецепт и
    ингредиентов, которые могут быть добавлены
     */
    protected static void prepareFormFields(Model model, Long recipe_id, RecipesToIngredientsDao recipesToIngredientsDao,
                                            IngredientDao ingredientDao) {
        List<IngredientProperty> ingredientsExistingInRecipe = recipesToIngredientsDao.getIngredientsForRecipe(recipe_id);
        model.addAttribute("ingredientsExistingInRecipe", ingredientsExistingInRecipe);
        List<Ingredient> ingredientsNotContainedInRecipe = getIngredientsNotContainedInRecipe(ingredientsExistingInRecipe, ingredientDao);
        model.addAttribute("ingredientsNotContainedInRecipe", ingredientsNotContainedInRecipe);
    }

    protected static List<Ingredient> getIngredientsNotContainedInRecipe(List<IngredientProperty> ingredientsExistingInRecipe,
                                                                         IngredientDao ingredientDao) {
        List<Ingredient> ingredientsAll = ingredientDao.getAll();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredientsExistingInRecipe.stream().forEach(p -> ingredients.add(p.getIngredient()));
        return ingredientsAll.stream()
                .filter(p -> ingredients.contains(p) == false).collect(Collectors.toList());
    }
}
