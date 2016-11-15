package ru.sbt.javaschool.recipes.springjdbc.entity;

/**
 * Created by скурихин on 14.11.2016.
 */
public class RecipesToIngredients {
    private Recipe recipe;
    private IngredientProperty ingredientProperty;

    public RecipesToIngredients(Recipe recipe, IngredientProperty ingredientProperty) {
        this.recipe = recipe;
        this.ingredientProperty = ingredientProperty;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipesToIngredients that = (RecipesToIngredients) o;

        if (recipe != null ? !recipe.equals(that.recipe) : that.recipe != null) return false;
        return ingredientProperty != null ? ingredientProperty.equals(that.ingredientProperty) : that.ingredientProperty == null;

    }

    @Override
    public int hashCode() {
        int result = recipe != null ? recipe.hashCode() : 0;
        result = 31 * result + (ingredientProperty != null ? ingredientProperty.hashCode() : 0);
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

