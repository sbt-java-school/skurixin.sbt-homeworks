package ru.sbt.javaschool.recipes.springjdbc.service;

import ru.sbt.javaschool.recipes.springjdbc.entity.Ingredient;

import java.util.List;

/**
 * Created by скурихин on 14.11.2016.
 */
public interface IngredientDao {
    Ingredient create(Ingredient ingredient);

    void delete(Long id);

    Ingredient edit(Long idSource, Ingredient ingredient);

    List<Ingredient> getByPartOfName(String name);

    List<Ingredient> getAll();
}
