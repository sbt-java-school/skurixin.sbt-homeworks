package ru.sbt.javaschool.recipes.springjdbc.service.impl.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.javaschool.recipes.springjdbc.entity.Ingredient;
import ru.sbt.javaschool.recipes.springjdbc.repository.IngredientRepository;
import ru.sbt.javaschool.recipes.springjdbc.service.IngredientDao;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
@Service
public class IngredientServiceImpl implements IngredientDao {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient edit(Long idSource, Ingredient ingredient) {
        return ingredientRepository.saveAndFlush(ingredient);
    }

    @Override
    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.saveAndFlush(ingredient);
    }

    @Override
    public void delete(Long id) {
        ingredientRepository.delete(id);
    }

    @Override
    public List<Ingredient> getByPartOfName(String name) {
        return ingredientRepository.findByNameContaining(name);
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }
}
