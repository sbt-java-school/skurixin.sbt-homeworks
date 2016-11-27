package ru.sbt.recipes.mvc.dataconfig;

/**
 * Created by скурихин on 14.11.2016.
 */
public interface TablesInitializer {
    void executeScript(String fileName);

    void createTables();

    void dropTables();
}
