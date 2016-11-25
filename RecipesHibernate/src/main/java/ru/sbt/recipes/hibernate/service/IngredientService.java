package ru.sbt.recipes.hibernate.service;

import ru.sbt.recipes.hibernate.entity.Ingredient;

import java.util.List;

/**
 * Created by скурихин on 23.11.2016.
 */
public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);

    void delete(Ingredient ingredient);

    Ingredient edit(Long idSource, Ingredient ingredient);

    List<Ingredient> getByPartOfName(String name);

    List<Ingredient> getAll();
}
