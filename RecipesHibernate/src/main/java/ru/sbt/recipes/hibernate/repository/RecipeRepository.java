package ru.sbt.recipes.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.recipes.hibernate.entity.Recipe;

import java.util.List;

/**
 * Created by скурихин on 22.11.2016.
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNameContaining(String name);
}
