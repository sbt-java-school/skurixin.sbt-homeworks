package ru.sbt.recipes.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbt.recipes.hibernate.entity.RecipesToIngredients;

/**
 * Created by скурихин on 23.11.2016.
 */
public interface RecipesToIngredientsRepository extends JpaRepository<RecipesToIngredients,Long> {
}
