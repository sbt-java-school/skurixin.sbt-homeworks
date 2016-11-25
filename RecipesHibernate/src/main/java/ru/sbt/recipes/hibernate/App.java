package ru.sbt.recipes.hibernate;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.recipes.hibernate.config.ApplicationConfiguration;
import ru.sbt.recipes.hibernate.entity.Ingredient;
import ru.sbt.recipes.hibernate.entity.Recipe;
import ru.sbt.recipes.hibernate.entity.RecipesToIngredients;
import ru.sbt.recipes.hibernate.service.IngredientService;
import ru.sbt.recipes.hibernate.service.RecipeService;
import ru.sbt.recipes.hibernate.service.RecipesToIngredientsService;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
public class App {

    static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        RecipeService recipeService = context.getBean(RecipeService.class);
        IngredientService ingredientService = context.getBean(IngredientService.class);
        RecipesToIngredientsService service = context.getBean(RecipesToIngredientsService.class);

        Recipe soup = recipeService.addRecipe(new Recipe("soup"));
        Recipe kotletki = recipeService.addRecipe(new Recipe("kotletki"));

        Ingredient water = ingredientService.addIngredient(new Ingredient("water"));
        Ingredient meat = ingredientService.addIngredient(new Ingredient("meat"));
        Ingredient metal = ingredientService.addIngredient(new Ingredient("metal!"));

        RecipesToIngredients add = service.add(new RecipesToIngredients(soup, water));
        RecipesToIngredients add1 = service.add(new RecipesToIngredients(kotletki, meat));
        RecipesToIngredients add2 = service.add(new RecipesToIngredients(soup, meat, 200));

        LOGGER.info("!!!!!!!!!!!!!!!!!!!");
        LOGGER.info(recipeService.getAll().toString());
        LOGGER.info("!!!!!!!!!!!!!!!!!!!");

        List<Ingredient> me = ingredientService.getByPartOfName("me");
        LOGGER.info(me.toString());
        LOGGER.info("!!!!!!!!!!!!!!!!!!!");

        List<Recipe> listO = recipeService.getRecipeByPartOfName("o");
        LOGGER.info(listO.toString());
        LOGGER.info("!!!!!!!!!!!!!!!!!!!");

    }
}
