package ru.sbt.javaschool.recipes.springjdbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ru.sbt.javaschool.recipes.springjdbc.service.RecipeDao;
import ru.sbt.javaschool.recipes.springjdbc.entity.Recipe;

import java.util.List;
import java.util.Map;

/**
 * Created by скурихин on 14.11.2016.
 */
@Service
public class RecipeDaoImpl implements RecipeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplatecTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Recipe create(Recipe recipe) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new BeanPropertySqlParameterSource(recipe);
        int result = namedParameterJdbcTemplatecTemplate.update("insert into Recipe values (:id,:name,:description);", params, keyHolder);
        recipe.setId(keyHolder.getKey().longValue());
        return recipe;
    }

    @Override
    public void delete(Long id) {
        namedParameterJdbcTemplatecTemplate.update("delete from Recipe where id=:id",
                new MapSqlParameterSource("id", id));
    }

    @Override
    public Recipe edit(Long idSource, Recipe values) {
        Recipe recipe = new Recipe(idSource, values.getName(), values.getDescription());
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(recipe);
        namedParameterJdbcTemplatecTemplate.update("update Recipe set name=:name,description=:description where id=:id", params);
        return recipe;
    }

    @Override
    public List<Recipe> getRecipeByPartOfName(String name) {
        return jdbcTemplate.query("select * from Recipe where name like '%" + name + "%'",
                (row,count)->new Recipe(row.getLong("id"),row.getString("name"),row.getString("description")));
    }

    @Override
    public Recipe getRecipe(Long id) {
        Map<String, Object> map = namedParameterJdbcTemplatecTemplate.queryForMap("select * from Recipe where id=:id",
                new MapSqlParameterSource("id", id));
        return new Recipe(
                ((Number) map.get("id")).longValue(),
                (String) map.get("name"),
                (String) map.get("description")
        );
    }

    @Override
    public List<Recipe> getAll() {
        return jdbcTemplate.query("select * from Recipe ORDER BY id",
                (row,count)->new Recipe(row.getLong("id"),row.getString("name"),row.getString("description")));
    }
}
