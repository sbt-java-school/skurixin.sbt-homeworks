package ru.sbt.recipes.mvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.recipes.mvc.entity.Recipe;

import java.util.List;

/**
 * Created by скурихин on 22.11.2016.
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNameContaining(String name);
}
