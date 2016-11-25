package ru.sbt.recipes.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.recipes.hibernate.entity.Ingredient;
import ru.sbt.recipes.hibernate.repository.IngredientRepository;
import ru.sbt.recipes.hibernate.service.IngredientService;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.saveAndFlush(ingredient);
    }

    @Override
    public void delete(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }

    @Override
    public Ingredient edit(Long idSource, Ingredient ingredient) {
        return ingredientRepository.saveAndFlush(ingredient);
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
