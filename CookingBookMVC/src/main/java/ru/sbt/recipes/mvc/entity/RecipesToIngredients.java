package ru.sbt.recipes.mvc.entity;

import javax.persistence.*;

/**
 * Created by скурихин on 14.11.2016.
 */
@Entity
@Table(name = "recipe_ingredient",
        uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"}))
public class RecipesToIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Transient
    private IngredientProperty ingredientProperty;

    private Long count;

    protected RecipesToIngredients(){}

    public RecipesToIngredients(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipesToIngredients(Recipe recipe, IngredientProperty ingredientProperty) {
        this.recipe = recipe;
        this.ingredientProperty = ingredientProperty;
    }

    public RecipesToIngredients(Recipe recipe, Ingredient ingredient, Long count) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.count = count;
    }

    public RecipesToIngredients(Recipe recipe, Ingredient ingredient) {
        this.recipe = recipe;
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public IngredientProperty getIngredientProperty() {
        return ingredientProperty;
    }

    public void setIngredientProperty(IngredientProperty ingredientProperty) {
        this.ingredientProperty = ingredientProperty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipesToIngredients that = (RecipesToIngredients) o;

        if (id != that.id) return false;
        if (count != that.count) return false;
        if (recipe != null ? !recipe.equals(that.recipe) : that.recipe != null) return false;
        if (ingredient != null ? !ingredient.equals(that.ingredient) : that.ingredient != null) return false;
        return ingredientProperty != null ? ingredientProperty.equals(that.ingredientProperty) : that.ingredientProperty == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (recipe != null ? recipe.hashCode() : 0);
        result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
        result = 31 * result + (ingredientProperty != null ? ingredientProperty.hashCode() : 0);
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "RecipesToIngredients{" +
                "recipe=" + recipe +
                ", ingredientProperty=" + ingredientProperty +
                '}';
    }
}

