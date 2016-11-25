package ru.sbt.javaschool.recipes.springjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.javaschool.recipes.springjdbc.entity.Recipe;

import java.util.List;

/**
 * Created by скурихин on 22.11.2016.
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNameContaining(String name);
}
