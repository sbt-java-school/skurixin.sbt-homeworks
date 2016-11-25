package ru.sbt.recipes.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.recipes.hibernate.entity.Recipe;
import ru.sbt.recipes.hibernate.repository.RecipeRepository;
import ru.sbt.recipes.hibernate.service.RecipeService;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    @Override
    public Recipe edit(Long idSource, Recipe values) {
        return recipeRepository.saveAndFlush(values);
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
