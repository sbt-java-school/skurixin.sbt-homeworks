package ru.sbt.recipes.mvc.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by скурихин on 14.11.2016.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER)
    List<RecipesToIngredients> recipes;

    protected Ingredient() {
    }

    public Ingredient(String name) {
        this(null, name);
    }

    public Ingredient(Long id) {
        this.id = id;
    }

    public Ingredient(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
