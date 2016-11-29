package ru.sbt.recipes.mvc.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by скурихин on 14.11.2016.
 */
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1,message = "Введите название рецепта")
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    private String description;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    List<RecipesToIngredients> ingredients;

    public Recipe(){}

    public Recipe(String name) {
        this.name = name;
    }

    public Recipe(Long id) {
        this.id = id;
    }

    public Recipe(Long id, String name) {
        this(name);
        this.id = id;
    }

    public Recipe(Long id, String name, String description) {
        this(id, name);
        this.description = description;
    }

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
        if (name != null ? !name.equals(recipe.name) : recipe.name != null) return false;
        return description != null ? description.equals(recipe.description) : recipe.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
