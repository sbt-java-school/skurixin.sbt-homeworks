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
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;


    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER)
    List<RecipesToIngredients> recipes;

    protected Ingredient(){
    }

    public Ingredient(String name, List<RecipesToIngredients> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    public Ingredient(String name) {
        this(name, new ArrayList<>());
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

    public List<RecipesToIngredients> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipesToIngredients> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return recipes != null ? recipes.equals(that.recipes) : that.recipes == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (recipes != null ? recipes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipes=" + recipes.toString() +
                '}';
    }
}
