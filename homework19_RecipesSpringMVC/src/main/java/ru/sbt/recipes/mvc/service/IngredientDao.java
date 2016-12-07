package ru.sbt.recipes.mvc.service;

import ru.sbt.recipes.mvc.entity.Ingredient;

import java.util.List;

public interface IngredientDao {
    Ingredient create(Ingredient ingredient);

    Ingredient get(Long id);

    Ingredient getByName(String name);

    void delete(Long id);

    Ingredient edit(Ingredient ingredient);

    List<Ingredient> getByPartOfName(String name);

    List<Ingredient> getAll();
}
