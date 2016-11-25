package ru.sbt.recipes.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.recipes.hibernate.entity.Ingredient;
import ru.sbt.recipes.hibernate.entity.Recipe;

import java.util.List;

/**
 * Created by скурихин on 22.11.2016.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameContaining(String name);
}
