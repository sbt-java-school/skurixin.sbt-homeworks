package ru.sbt.recipes.mvc.dto;

/**
 * Created by скурихин on 28.11.2016.
 */
public class RecipeDto {
    private Long id;
    private String name;
    private String description;

    public RecipeDto() {
    }

    public RecipeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RecipeDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RecipeDto(Long id, String name, String description) {
        this.id = id;
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

        RecipeDto recipeDto = (RecipeDto) o;

        if (id != null ? !id.equals(recipeDto.id) : recipeDto.id != null) return false;
        if (name != null ? !name.equals(recipeDto.name) : recipeDto.name != null) return false;
        return description != null ? description.equals(recipeDto.description) : recipeDto.description == null;

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
        return "RecipeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
