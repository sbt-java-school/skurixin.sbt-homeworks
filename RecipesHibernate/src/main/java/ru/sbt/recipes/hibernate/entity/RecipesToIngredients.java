package ru.sbt.recipes.hibernate.entity;

import javax.persistence.*;

/**
 * Created by скурихин on 23.11.2016.
 */
@Entity
public class RecipesToIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private long count;

    protected RecipesToIngredients(){

    }

    public RecipesToIngredients(Recipe recipe, Ingredient ingredient, long count) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.count = count;
    }

    public RecipesToIngredients(Recipe recipe,Ingredient ingredient){
        this(recipe,ingredient,0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
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
        return ingredient != null ? ingredient.equals(that.ingredient) : that.ingredient == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (recipe != null ? recipe.hashCode() : 0);
        result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "RecipesToIngredients{" +
                "id=" + id +
                ", recipe=" + recipe.getName() +
                ", ingredient=" + ingredient.getName() +
                ", count=" + count +
                '}';
    }
}
