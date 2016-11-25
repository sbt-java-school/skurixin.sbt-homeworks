package ru.sbt.recipes.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.recipes.hibernate.entity.RecipesToIngredients;
import ru.sbt.recipes.hibernate.repository.RecipeRepository;
import ru.sbt.recipes.hibernate.repository.RecipesToIngredientsRepository;
import ru.sbt.recipes.hibernate.service.RecipesToIngredientsService;

/**
 * Created by скурихин on 23.11.2016.
 */
@Service
public class RecipesToIngredientsServiceImpl implements RecipesToIngredientsService {
    @Autowired
    private RecipesToIngredientsRepository recipesToIngredientsRepository;


    @Override
    public RecipesToIngredients add(RecipesToIngredients recipesToIngredients) {
        return recipesToIngredientsRepository.saveAndFlush(recipesToIngredients);
    }
}
