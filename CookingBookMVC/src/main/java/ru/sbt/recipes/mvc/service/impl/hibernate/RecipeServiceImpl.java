package ru.sbt.recipes.mvc.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.repository.RecipeRepository;
import ru.sbt.recipes.mvc.service.RecipeDao;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Created by скурихин on 23.11.2016.
 */
@Service
public class RecipeServiceImpl implements RecipeDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Recipe edit(Long idSource, Recipe values) {
        return recipeRepository.saveAndFlush(values);
    }

    @Override
    public Recipe create(Recipe recipe) {
//        return recipeRepository.saveAndFlush(recipe);
        entityManager.persist(recipe);
        return recipe;
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
//        Recipe one = recipeRepository.getOne(id);
//        return one;
        Recipe recipe = entityManager.find(Recipe.class, id);
        return recipe;
    }

    @Override
    public List<Recipe> getAll() {
//        return recipeRepository.findAll();
        return entityManager.createQuery("select i from Recipe i").getResultList();
    }

    @Override
    public Recipe update(Recipe recipe) {
        return entityManager.merge(recipe);
    }
}
