package ru.sbt.javaschool.recipes.springjdbc.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ru.sbt.javaschool.recipes.springjdbc.dao.IngredientDao;
import ru.sbt.javaschool.recipes.springjdbc.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by скурихин on 14.11.2016.
 */
@Service
public class IngredientDaoImpl implements IngredientDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplatecTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Ingredient create(Ingredient ingredient) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new BeanPropertySqlParameterSource(ingredient);
        namedParameterJdbcTemplatecTemplate.update("insert into Ingredient values (:id,:name);", params, keyHolder);
        ingredient.setId(keyHolder.getKey().longValue());
        return ingredient;
    }

    @Override
    public void delete(Long id) {
        namedParameterJdbcTemplatecTemplate.update("delete from Ingredient where id=:id",
                new MapSqlParameterSource("id", id));
    }

    @Override
    public Ingredient edit(Long idSource, Ingredient values) {
        Ingredient ingredient = new Ingredient(idSource, values.getName());
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(ingredient);
        int update = namedParameterJdbcTemplatecTemplate.update("update Ingredients set name=:name where id=:id", params);
        return ingredient;
    }

    @Override
    public List<Ingredient> getByPartOfName(String name) {
        List<Map<String, Object>> list = namedParameterJdbcTemplatecTemplate.queryForList("select id,name from Ingredient where name like '%:name%'",
                new MapSqlParameterSource("name", name));
        ArrayList<Ingredient> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            result.add(new Ingredient(
                    ((Number) map.get("id")).longValue(),
                    (String) map.get("name")
            ));
        }
        return result;
    }

    @Override
    public List<Ingredient> getAll() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,name from Ingredient ing ORDER BY 1");
        ArrayList<Ingredient> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(new Ingredient(
                    ((Number) map.get("id")).longValue(),
                    (String) map.get("name")
            ));
        }
        return result;
    }
}
