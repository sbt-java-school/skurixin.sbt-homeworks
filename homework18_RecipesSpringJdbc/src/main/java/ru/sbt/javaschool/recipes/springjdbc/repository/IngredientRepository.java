package ru.sbt.javaschool.recipes.springjdbc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.javaschool.recipes.springjdbc.entity.Ingredient;

import java.util.List;

/**
 * Created by скурихин on 22.11.2016.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameContaining(String name);
}
