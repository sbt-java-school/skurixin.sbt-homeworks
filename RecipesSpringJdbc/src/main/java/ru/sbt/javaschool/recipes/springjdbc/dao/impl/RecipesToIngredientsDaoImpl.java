package ru.sbt.javaschool.recipes.springjdbc.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.sbt.javaschool.recipes.springjdbc.dao.RecipesToIngredientsDao;
import ru.sbt.javaschool.recipes.springjdbc.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by скурихин on 14.11.2016.
 */
@Service
public class RecipesToIngredientsDaoImpl implements RecipesToIngredientsDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addIngredientToRecipe(Recipe recipe, IngredientProperty ingredientProperty) {
        namedParameterJdbcTemplate.update("insert into RecipeToIngredients values(:r_id,:i_id,:i_count)",
                new MapSqlParameterSource(new HashMap<String, Object>() {{
                    put("r_id", recipe.getId());
                    put("i_id", ingredientProperty.getIngredient().getId());
                    put("i_count", ingredientProperty.getCount());
                }})
        );
    }

    @Override
    public Map<Recipe, List<IngredientProperty>> getCookingBook() {
        HashMap<Recipe, List<IngredientProperty>> resultMap = new HashMap<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select r_id,r.name as recipe_name,r.description,i_id,i.name as ing_name,i_count from RecipeToIngredients " +
                "left join Recipe r on r_id=r.id " +
                "left join Ingredient i on i_id=i.id ORDER BY 1,4");
        for (Map<String, Object> map : list) {
            Recipe recipe = new Recipe(
                    ((Number) map.get("r_id")).longValue(),
                    ((String) map.get("recipe_name")),
                    ((String) map.get("description")));
            IngredientProperty ingredientProperty = new IngredientProperty(
                    new Ingredient(
                            ((Number) map.get("i_id")).longValue(),
                            ((String) map.get("ing_name"))
                    ),
                    ((Number) map.get("i_count")).longValue(),
                    Measure.COUNT
            );
            if (!resultMap.containsKey(recipe)) {
                resultMap.put(recipe, new ArrayList<>());
            }
            resultMap.get(recipe).add(ingredientProperty);
        }
        return resultMap;
    }

    @Override
    public RecipesToIngredients updateCount(Recipe recipe, Ingredient ingredient, Long i_count) {
        int update = namedParameterJdbcTemplate.update("update RecipeToIngredients set i_count=:i_count where r_id=:r_id and i_id=:i_id",
                new MapSqlParameterSource(new HashMap<String, Object>() {{
                    put("r_id", recipe.getId());
                    put("i_id", ingredient.getId());
                    put("i_count", i_count);
                }})
        );
        return update == 1 ? new RecipesToIngredients(recipe, new IngredientProperty(ingredient, i_count, Measure.COUNT)) : null;
    }

    @Override
    public boolean deleteIngredientFromRecipe(Recipe recipe, Ingredient ingredient) {
        int update = namedParameterJdbcTemplate.update("delete from RecipeToIngredients where r_id=:r_id and i_id=:i_id",
                new MapSqlParameterSource(new HashMap<String, Object>() {{
                    put("r_id", recipe.getId());
                    put("i_id", ingredient.getId());
                }})
        );
        return update == 1;
    }

    @Override
    public List<IngredientProperty> getIngredientsForRecipe(Recipe recipe) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(recipe);
        return namedParameterJdbcTemplate.query("select id,name,i_count " +
                        "from RecipeToIngredients rec left join Ingredient ing on rec.i_id=ing.id " +
                        "where rec.r_id=:id", params,
                (row, count) -> new IngredientProperty(new Ingredient(row.getLong("id"), row.getString("name"))
                        , row.getLong("i_count")));
    }
}
