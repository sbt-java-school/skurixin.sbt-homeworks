package ru.sbt.recipes.mvc.dataconfig;

//import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by скурихин on 14.11.2016.
 */
@Service
public class TablesInitializerImpl implements TablesInitializer {
    private static final Logger LOGGER = Logger.getLogger(TablesInitializerImpl.class);
    public static final String SCRIPT_CREATE = "CookingBookMVC/src/main/resources/sql/createTables.sql";
    public static final String SCRIPT_DROP = "CookingBookMVC/src/main/resources/sql/dropTables.sql";
    public static final String SCRIPT_FILL = "CookingBookMVC/src/main/resources/sql/fillTable.sql";
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void executeScript(String fileName) {
//        try {
        String script = "asdfaf"/* = FileUtils.readFileToString(new File(fileName))*/;
        jdbcTemplate.execute(script);
//        } catch (IOException e) {
//            LOGGER.error("Ошибка создания таблиц");
//        }
    }

    @Override
    public void createTables() {
        executeScript(SCRIPT_CREATE);
    }

    @Override
    public void dropTables() {
        executeScript(SCRIPT_DROP);
    }
}
