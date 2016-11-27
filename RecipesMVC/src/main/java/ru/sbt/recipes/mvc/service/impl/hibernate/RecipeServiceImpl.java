package ru.sbt.recipes.mvc.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.repository.RecipeRepository;
import ru.sbt.recipes.mvc.service.RecipeDao;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
@Service
public class RecipeServiceImpl implements RecipeDao {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Recipe edit(Long idSource, Recipe values) {
        return recipeRepository.saveAndFlush(values);
    }

    @Override
    public Recipe create(Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public void delete(Long id) {
        recipeRepository.delete(id);
    }

    @Override
    public List<Recipe> getRecipeByPartOfName(String name) {
        return recipeRepository.findByNameContaining(name);
    }

    @Override
    public Recipe getRecipe(Long id) {
        return recipeRepository.getOne(id);
    }

    @Override
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }
}
