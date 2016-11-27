package ru.sbt.recipes.mvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.recipes.mvc.entity.Ingredient;

import java.util.List;

/**
 * Created by скурихин on 22.11.2016.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameContaining(String name);
}
