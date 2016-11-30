package ru.sbt.recipes.mvc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

    //start page
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<Recipe> all = recipeDao.getAll();
        model.put("list", all);
        return "index";
    }

    //создание рецепта
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showFormAddRecipe(Model model) {
        Recipe recipe = new Recipe();
        recipe.setName("defaultName");
        recipe.setDescription("defaultDescription");

        model.addAttribute("recipeFORM", recipe);
        return "create_edit_recipe";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
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

    @RequestMapping(value = "{id}")
    public String ingredientsForRecipe(Model model,
                                       @PathVariable("id") Long recipe_id,
                                       HttpSession httpSession) {
        Recipe recipe = recipeDao.getRecipe(recipe_id);
        RecipesToIngredients relation = new RecipesToIngredients(recipe);
        model.addAttribute("recipeCurrent", recipe);
        model.addAttribute("recipeToIngredient", relation);

        prepareFormFields(model, recipe_id);

        httpSession.setAttribute("recipeCurrent", recipe);
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
        return "create_edit_recipe";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public String saveRecipeToIngredient(Model model,
                                         @ModelAttribute("recipeToIngredient") @Valid RecipesToIngredients recipeToIngredient,
                                         HttpSession httpSession,
                                         BindingResult bindingResult) {
        Recipe recipe = (Recipe) httpSession.getAttribute("recipeCurrent");
        if (recipeToIngredient.getCount() <= 0) {
            prepareFormFields(model, recipe.getId());
            recipeToIngredient.setIngredient(null);
            recipeToIngredient.setCount(1L);
            bindingResult.rejectValue("count", "error.recipeToIngredient", "Колличество должно быть больше 0");
            return "recipe";
        }
        if ("".equals(recipeToIngredient.getIngredient().getName())) {
            prepareFormFields(model, recipe.getId());
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
                prepareFormFields(model, recipe.getId());
                recipeToIngredient.setIngredient(null);
                bindingResult.rejectValue("ingredient", "error.recipeToIngredient", "Данный ингредиент уже добавлен в рецепт");
            }
        }
        if (bindingResult.hasErrors()) {
            return "recipe";
        }
        return "redirect:/recipes/" + recipe.getId();
    }

    private void prepareFormFields(Model model, Long recipe_id) {
        List<IngredientProperty> ingredientsExistingInRecipe = recipesToIngredientsDao.getIngredientsForRecipe(recipe_id);
        model.addAttribute("ingredientsExistingInRecipe", ingredientsExistingInRecipe);
        List<Ingredient> ingredientsNotContainedInRecipe = getIngredientsNotContainedInRecipe(ingredientsExistingInRecipe);
        model.addAttribute("ingredientsNotContainedInRecipe", ingredientsNotContainedInRecipe);
    }

    private List<Ingredient> getIngredientsNotContainedInRecipe(List<IngredientProperty> ingredientsExistingInRecipe) {
        List<Ingredient> ingredientsAll = ingredientDao.getAll();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredientsExistingInRecipe.stream().forEach(p -> ingredients.add(p.getIngredient()));
        return ingredientsAll.stream()
                .filter(p -> ingredients.contains(p) == false).collect(Collectors.toList());
    }
}
