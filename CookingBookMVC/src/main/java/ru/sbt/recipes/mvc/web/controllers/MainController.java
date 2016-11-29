package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
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

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(ModelMap model) {
        Recipe borstch = recipeDao.create(new Recipe("borstch"));
        Recipe kompot = recipeDao.create(new Recipe("kompot"));
        Recipe beaf = recipeDao.create(new Recipe("beaf"));
        Ingredient look = ingredientDao.create(new Ingredient("look"));
        Ingredient svekla = ingredientDao.create(new Ingredient("svekla"));
        Ingredient water = ingredientDao.create(new Ingredient("water"));
        Ingredient yagodi = ingredientDao.create(new Ingredient("yagodi"));
        Ingredient meat = ingredientDao.create(new Ingredient("meat"));
        recipesToIngredientsDao.addIngredientToRecipe(borstch, new IngredientProperty(look, 100L));
        recipesToIngredientsDao.addIngredientToRecipe(borstch, new IngredientProperty(svekla, 200L));
        recipesToIngredientsDao.addIngredientToRecipe(borstch, new IngredientProperty(water, 50L));
        recipesToIngredientsDao.addIngredientToRecipe(kompot, new IngredientProperty(water, 100L));
        recipesToIngredientsDao.addIngredientToRecipe(kompot, new IngredientProperty(yagodi, 20L));
        recipesToIngredientsDao.addIngredientToRecipe(beaf, new IngredientProperty(meat, 200L));
        recipesToIngredientsDao.addIngredientToRecipe(beaf, new IngredientProperty(look, 50L));

        return "redirect:/recipes";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<Recipe> all = recipeDao.getAll();
        model.put("list", all);
        return "index";
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createRecipeFromForm(@ModelAttribute("recipeFORM") @Validated Recipe recipe) {
        if (recipe.getId() != null) {
            recipeDao.update(recipe);
        } else {
            recipeDao.create(recipe);
        }
        return "redirect:/recipes";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showFormAddRecipe(Model model) {
        Recipe recipe = new Recipe();
        recipe.setName("defaultName");
        recipe.setDescription("defaultDescription");

        model.addAttribute("recipeFORM", recipe);
        return "create_recipe";
    }

    @RequestMapping(value = "{id}")
    public String ingredientsForRecipe(Model model,
                                       @PathVariable("id") Long recipe_id,
                                       HttpSession httpSession) {
        Recipe recipe = recipeDao.getRecipe(recipe_id);
        model.addAttribute("recipeCurrent", recipe);
        List<IngredientProperty> list = recipesToIngredientsDao.getIngredientsForRecipe(recipe_id);
        model.addAttribute("ingredients", list);

        List<Ingredient> ingredientsAll = ingredientDao.getAll();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        list.stream().forEach(p->ingredients.add(p.getIngredient()));
        List<Ingredient> listNotContains = ingredientsAll.stream()
                .filter(p -> ingredients.contains(p) == false).collect(Collectors.toList());

        RecipesToIngredients recipesToIngredients = new RecipesToIngredients(recipe);
        model.addAttribute("recipeToIngredient",recipesToIngredients);
        model.addAttribute("listNotContains",listNotContains);

        httpSession.setAttribute("recipeCurrent",recipe);
        return "recipe";
    }

    @RequestMapping(value = "{id}/delete")
    public String deleteRecipe(@PathVariable("id") Long recipe_id) {
        recipesToIngredientsDao.deleteAllByRecipe(recipe_id);
        recipeDao.delete(recipe_id);
        return "redirect:/recipes";
    }

    @RequestMapping(value = "{id}/edit")
    public String editRecipe(@PathVariable("id") Long recipe_id,
                             Model model,
                             HttpSession httpSession) {
        Recipe recipe = recipeDao.getRecipe(recipe_id);
        model.addAttribute("recipeFORM", recipe);
        return "create_recipe";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public String saveRecipeToIngredient(
            Model model,
            @ModelAttribute("recipeToIngredient") RecipesToIngredients recipeToIngredient,
            HttpSession httpSession) {
        Recipe recipe = (Recipe) httpSession.getAttribute("recipeCurrent");
        Ingredient ingredient = (Ingredient) httpSession.getAttribute("ingredient");
        if (recipeToIngredient.getId() != null) {
            recipesToIngredientsDao.updateCount(recipe, ingredient, recipeToIngredient.getCount());
        }
        else{
            ingredient = ingredientDao.getByName(recipeToIngredient.getIngredient().getName());
            if(ingredient==null) {
                ingredient = ingredientDao.create(recipeToIngredient.getIngredient());
            }
            recipesToIngredientsDao.addIngredientToRecipe(recipe,
                    new IngredientProperty(ingredient,recipeToIngredient.getCount()));
        }
        return "redirect:/recipes/" + recipe.getId();
    }
}
