package ru.sbt.recipes.hibernate.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by скурихин on 21.11.2016.
 */
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    List<RecipesToIngredients> ingredients;

    protected Recipe(){

    }

    public Recipe(String name, String description, List<RecipesToIngredients> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    public Recipe(String name, String description) {
        this(name,description,new ArrayList<>());
    }

    public Recipe(String name) {
        this(name,null,new ArrayList<RecipesToIngredients>());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RecipesToIngredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipesToIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;
        if (name != null ? !name.equals(recipe.name) : recipe.name != null) return false;
        if (description != null ? !description.equals(recipe.description) : recipe.description != null) return false;
        return ingredients != null ? ingredients.equals(recipe.ingredients) : recipe.ingredients == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients.toString() +
                '}';
    }
}
