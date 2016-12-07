package ru.sbt.recipes.mvc.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.recipes.mvc.entity.Recipe;
import ru.sbt.recipes.mvc.repository.RecipeRepository;
import ru.sbt.recipes.mvc.service.RecipeDao;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Created by скурихин on 23.11.2016.
 */
@Service
@Transactional//(noRollbackFor = PersistenceException.class)
public class RecipeServiceImpl implements RecipeDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public int createIfNotExist(Recipe recipe) {
//        String name = recipe.getName();
//        int update = jdbcTemplate.update("insert into recipe(name)" +
//                "select distinct " + name + " from recipe r1 where not exist (select r2.name from recipe r2 where r2.name=\"+name+\")");
        Recipe byName = recipeRepository.findByName(recipe.getName());
        if (byName == null) {
            entityManager.persist(recipe);
            return 1;
        }
        return 0;
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
        return recipeRepository.findAll();
//        return entityManager.createQuery("select i from Recipe i").getResultList();
    }

    @Override
    public Recipe update(Recipe recipe) {
        return entityManager.merge(recipe);
    }

    @Override
    public int updateIfNotExist(Recipe recipe) {
        Recipe byName = recipeRepository.findByName(recipe.getName());
        if (byName == null) {
            entityManager.merge(recipe);
            return 1;
        }
        return 0;
    }
}
