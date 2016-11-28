package ru.sbt.recipes.mvc.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.entity.IngredientProperty;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.entity.RecipesToIngredients;
import ru.sbt.recipes.mvc.repository.RecipesToIngredientsRepository;
import ru.sbt.recipes.mvc.service.RecipesToIngredientsDao;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by скурихин on 23.11.2016.
 */
@Service
public class RecipesToIngredientsServiceImpl implements RecipesToIngredientsDao {
    @Autowired
    private RecipesToIngredientsRepository recipesToIngredientsRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void addIngredientToRecipe(Recipe recipe, IngredientProperty ingredientProperty) {
        recipesToIngredientsRepository.saveAndFlush(
                new RecipesToIngredients(recipe, ingredientProperty.getIngredient(), ingredientProperty.getCount()));
    }

    @Override
    public Map<Recipe, List<IngredientProperty>> getCookingBook() {
        List<RecipesToIngredients> all = recipesToIngredientsRepository.findAll();
        HashMap<Recipe, List<IngredientProperty>> resultMap = new HashMap<>();
        for (RecipesToIngredients field : all) {
            if (!resultMap.containsKey(field.getRecipe())) {
                ArrayList<IngredientProperty> list = new ArrayList<>();
                list.add(new IngredientProperty(field.getIngredient(), field.getCount()));
                resultMap.put(field.getRecipe(), list);
            } else {
                resultMap.get(field.getRecipe()).add(new IngredientProperty(field.getIngredient(), field.getCount()));
            }
        }
        return resultMap;
    }

    @Override
    public RecipesToIngredients updateCount(Recipe recipe, Ingredient ingredient, Long i_count) {
        RecipesToIngredients recipesToIngredients = recipesToIngredientsRepository.findByRecipeAndIngredient(recipe, ingredient);
        recipesToIngredients.setCount(i_count);
        return recipesToIngredientsRepository.saveAndFlush(recipesToIngredients);
    }

    @Override
    public boolean deleteIngredientFromRecipe(Recipe recipe, Ingredient ingredient) {
        RecipesToIngredients recipesToIngredients = recipesToIngredientsRepository.findByRecipeAndIngredient(recipe, ingredient);
        recipesToIngredientsRepository.delete(recipesToIngredients);
        return true;
    }

    @Override
    public List<IngredientProperty> getIngredientsForRecipe(Recipe recipe) {
        List<RecipesToIngredients> list = recipesToIngredientsRepository.findByRecipe(recipe);
        ArrayList<IngredientProperty> result = new ArrayList<>();
        for (RecipesToIngredients recipesToIngredients : list) {
            result.add(new IngredientProperty(recipesToIngredients.getIngredient(), recipesToIngredients.getCount()));
        }
        return result;
    }

    @Override
    public List<IngredientProperty> getIngredientsForRecipe(Long recipe_id) {
        return getIngredientsForRecipe(new Recipe(recipe_id, "name"));
    }

    @Override
    public int deleteAllByRecipe(Long recipe_id) {
        List<RecipesToIngredients> list = recipesToIngredientsRepository.findByRecipe(new Recipe(recipe_id, "temp"));
        list.stream().forEach(recipesToIngredientsRepository::delete);
        return 1;
    }
}
