package ru.sbt.recipes.mvc.entity;

/**
 * Created by скурихин on 14.11.2016.
 */
public class IngredientProperty {
    private Ingredient ingredient;
    private Long count;
    private Measure measure;

    public IngredientProperty(Ingredient ingredient, Long count) {
        this(ingredient, count, Measure.COUNT);
    }

    public IngredientProperty(Ingredient ingredient, Long count, Measure measure) {
        this.ingredient = ingredient;
        this.count = count;
        this.measure = measure;
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

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngredientProperty that = (IngredientProperty) o;

        if (ingredient != null ? !ingredient.equals(that.ingredient) : that.ingredient != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        return measure == that.measure;

    }

    @Override
    public int hashCode() {
        int result = ingredient != null ? ingredient.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (measure != null ? measure.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IngredientProperty{" +
                "ingredient=" + ingredient +
                ", count=" + count +
                ", measure=" + measure +
                '}';
    }
}
