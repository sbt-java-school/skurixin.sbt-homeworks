package ru.sbt.javaschool.recipes.springjdbc;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.javaschool.recipes.springjdbc.config.ApplicationConfiguration;
import ru.sbt.javaschool.recipes.springjdbc.config.TablesInitializer;
import ru.sbt.javaschool.recipes.springjdbc.config.TablesInitializerImpl;
import ru.sbt.javaschool.recipes.springjdbc.dao.IngredientDao;
import ru.sbt.javaschool.recipes.springjdbc.dao.RecipeDao;
import ru.sbt.javaschool.recipes.springjdbc.dao.RecipesToIngredientsDao;
import ru.sbt.javaschool.recipes.springjdbc.entity.Ingredient;
import ru.sbt.javaschool.recipes.springjdbc.entity.IngredientProperty;
import ru.sbt.javaschool.recipes.springjdbc.entity.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Service
public class CookingBookMain {
    // Инициализация логера
    private static final Logger LOGGER = Logger.getLogger(CookingBookMain.class);

    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private RecipesToIngredientsDao recipesToIngredientsDao;

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        TablesInitializer initializer = context.getBean(TablesInitializerImpl.class);
//        initializer.dropTables();
//        initializer.createTables();
//        initializer.executeScript(TablesInitializerImpl.SCRIPT_FILL);

        CookingBookMain book = context.getBean(CookingBookMain.class);
        book.start();
    }

    private void start() throws IOException {
        LOGGER.info("Start Application!");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        int action;
        metka:
        do {
            LOGGER.info("----Главное меню----");
            LOGGER.info("1. Поиск рецепта");
            LOGGER.info("2. Создать рецепт");
            LOGGER.info("3. Вывод кулинарной книги");
            LOGGER.info("4. Вывод ингредиентов");
            LOGGER.info("5. Вывод рецептов");
            LOGGER.info("0. Выход");
            LOGGER.info("Выберите действие: ");
            action = getUserAction(consoleReader);
            switch (action) {
                case 1: {
                    search(consoleReader);
                    break;
                }
                case 2: {
                    createRecipe(consoleReader);
                    break;
                }
                case 3: {
                    showBook();
                    break;
                }
                case 4: {
                    ingredientDao.getAll().stream().forEach(System.out::println);
                    break;
                }
                case 5: {
                    recipeDao.getAll().stream().forEach(System.out::println);
                    break;
                }
                case 0:
                    break metka;
                default: {
                    LOGGER.info("Выберите корректную операцию");
                }
            }
        } while (action != 0);
    }

    private int getUserAction(BufferedReader consoleReader) throws IOException {
        int result = 0;
        while (true) {
            try {
                result = Integer.parseInt(consoleReader.readLine());
                break;
            } catch (NumberFormatException e) {
                LOGGER.error("ОШИБКА! Введите номер действия.");
                LOGGER.error("Попробуйте еще раз:");
                continue;
            }
        }
        return result;
    }

    private int getSelectedIngredientFromList(BufferedReader consoleReader, int maxInd, boolean isExitExist) throws IOException {
        int result = 0;
        while (true) {
            try {
                result = Integer.parseInt(consoleReader.readLine());
                if ((result >= 1 && result <= maxInd) || (isExitExist && result == 0))
                    break;
                else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                LOGGER.error("ОШИБКА! Выберите корректный элемент списка.");
                LOGGER.error("Попробуйте еще раз:");
                continue;
            }
        }
        return result;
    }

    private void showBook() {
        Map<Recipe, List<IngredientProperty>> cookingBook = recipesToIngredientsDao.getCookingBook();
        for (Map.Entry<Recipe, List<IngredientProperty>> entry : cookingBook.entrySet()) {
            LOGGER.info(entry.getKey().toString());
            entry.getValue().stream().forEach(l -> LOGGER.info("   " + l.toString()));
        }
    }

    private void createRecipe(BufferedReader consoleReader) throws IOException {
        while (true) {
            try {
                LOGGER.info("Введите название рецепта");
                String name = consoleReader.readLine();
                Recipe recipe = recipeDao.create(new Recipe(name));
                workWithIngredients(recipe, consoleReader);
                break;
            } catch (DuplicateKeyException e) {
                LOGGER.error("ОШИБКА! Рецепт с таким именем уже существует");
                LOGGER.error("Попробуйте еще раз");
                continue;
            }
        }
    }

    private void search(BufferedReader consoleReader) throws IOException {
        LOGGER.info("Введите название рецепта");
        String nameRecipe = consoleReader.readLine();
        List<Recipe> list = recipeDao.getRecipeByPartOfName(nameRecipe);
        for (int i = 0; i < list.size(); i++) {
            LOGGER.info("" + (i + 1) + ". " + list.get(i).getName());
        }
        LOGGER.info("0. В главное меню");
        int action = getSelectedIngredientFromList(consoleReader, list.size(), true);
        if (action != 0)
            workWithIngredients(list.get(action - 1), consoleReader);
    }

    private void workWithIngredients(Recipe recipe, BufferedReader consoleReader) throws IOException {
        int action;
        do {
            List<IngredientProperty> ingredients = recipesToIngredientsDao.getIngredientsForRecipe(recipe);
            LOGGER.info("Ингредиенты:");
            for (int i = 0; i < ingredients.size(); i++) {
                LOGGER.info("" + (i + 1) + ". " + ingredients.get(i));
            }
            LOGGER.info("Выберите действие:");
            LOGGER.info("1. Добавить существующий ингредиент");
            LOGGER.info("2. Изменить количество ингредиента");
            LOGGER.info("3. Удалить ингредиент из рецепта");
            LOGGER.info("4. Создать новый ингредиент");
            LOGGER.info("0. В главное меню");
            action = getUserAction(consoleReader);
            switch (action) {
                case 1:
                    addIngredient(recipe, consoleReader);
                    break;
                case 2:
                    editIngredient(recipe, ingredients, consoleReader);
                    break;
                case 3:
                    deleteIngredient(recipe, ingredients, consoleReader);
                    break;
                case 4:
                    createIngredient(consoleReader);
                    break;
            }
        } while (action != 0);
    }

    private void createIngredient(BufferedReader consoleReader) throws IOException {
        LOGGER.info("Введите название нового ингредиента");
        String name = consoleReader.readLine();
        try {
            ingredientDao.create(new Ingredient(name));
        } catch (DuplicateKeyException e) {
            LOGGER.error("ОШИБКА! Рецепт с таким именем уже существует в БД.");
        }
    }

    private void deleteIngredient(Recipe recipe, List<IngredientProperty> ingredients, BufferedReader consoleReader) throws IOException {
        LOGGER.info("Выберите удаляемый ингредиент");
        int ing = getSelectedIngredientFromList(consoleReader, ingredients.size(), false) - 1;
        recipesToIngredientsDao.deleteIngredientFromRecipe(recipe, ingredients.get(ing).getIngredient());
    }

    private void editIngredient(Recipe recipe, List<IngredientProperty> ingredients, BufferedReader consoleReader) throws IOException {
        LOGGER.info("Выберите редактируемый ингредиент");
        int ing = getSelectedIngredientFromList(consoleReader, ingredients.size(), false) - 1;
        LOGGER.info("Введите новое количество");
        long count = Long.parseLong(consoleReader.readLine());
        IngredientProperty ingredientProperty = ingredients.get(ing);
        try {
            recipesToIngredientsDao.updateCount(recipe, ingredientProperty.getIngredient(), count);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("ОШИБКА! Не корректные данные.");
            LOGGER.error("Попробуйте еще раз:");
        }
    }

    private void addIngredient(Recipe recipe, BufferedReader consoleReader) throws IOException {
        while (true) {
            try {
                List<Ingredient> allIngredients = ingredientDao.getAll();
                LOGGER.info("Ингредиенты:");
                for (int i = 0; i < allIngredients.size(); i++) {
                    LOGGER.info("" + (i + 1) + ". " + allIngredients.get(i));
                }
                LOGGER.info("0. Назад:");
                int action = getSelectedIngredientFromList(consoleReader, allIngredients.size(), true);
                if (action != 0) {
                    LOGGER.info("Колличество:");
                    long count = Long.parseLong(consoleReader.readLine());
                    recipesToIngredientsDao.addIngredientToRecipe(recipe, new IngredientProperty(allIngredients.get(action - 1), count));
                }
                break;
            } catch (DuplicateKeyException e) {
                LOGGER.error("ОШИБКА! Данный ингредиент уже добавлен в этот рецепт.");
                LOGGER.error("Попробуйте еще раз:");
                continue;
            } catch (DataIntegrityViolationException e) {
                LOGGER.error("ОШИБКА! Не корректные данные.");
                LOGGER.error("Попробуйте еще раз:");
            }
        }
    }
}
