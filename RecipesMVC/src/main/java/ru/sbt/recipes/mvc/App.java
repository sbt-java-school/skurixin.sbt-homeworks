package ru.sbt.recipes.mvc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.recipes.mvc.dataconfig.ApplicationConfigurationWithHibernate;
import ru.sbt.recipes.mvc.entity.Ingredient;
import ru.sbt.recipes.mvc.service.IngredientDao;

/**
 * Created by скурихин on 28.11.2016.
 */
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationWithHibernate.class);
        IngredientDao bean = context.getBean(IngredientDao.class);
        bean.create(new Ingredient("nsdf"));
    }
}
