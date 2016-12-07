package ru.sbt.javaschool.recipes.springjdbc.config;

//import org.hibernate.ejb.HibernatePersistence;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.sbt.javaschool.recipes.springjdbc.service.RecipeDao;
import ru.sbt.javaschool.recipes.springjdbc.service.RecipesToIngredientsDao;
import ru.sbt.javaschool.recipes.springjdbc.service.impl.IngredientDaoImpl;
import ru.sbt.javaschool.recipes.springjdbc.service.impl.RecipeDaoImpl;
import ru.sbt.javaschool.recipes.springjdbc.service.impl.RecipesToIngredientsDaoImpl;
import ru.sbt.javaschool.recipes.springjdbc.service.impl.hibernate.IngredientServiceImpl;
import ru.sbt.javaschool.recipes.springjdbc.service.impl.hibernate.RecipeServiceImpl;
import ru.sbt.javaschool.recipes.springjdbc.service.impl.hibernate.RecipesToIngredientsServiceImpl;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by скурихин on 21.11.2016.
 */
@Configuration
//@ComponentScan("ru.sbt.javaschool.recipes.springjdbc")

@ComponentScan(basePackages = {"ru.sbt.javaschool.recipes.springjdbc"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = IngredientDaoImpl.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RecipeDaoImpl.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RecipesToIngredientsDaoImpl.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ApplicationConfiguration.class)})
@EnableJpaRepositories("ru.sbt.javaschool.recipes.springjdbc.repository")
@EnableTransactionManagement
public class ApplicationConfigurationWithHibernate {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource managerDataSource = new DriverManagerDataSource();
        managerDataSource.setDriverClassName("org.h2.Driver");
        managerDataSource.setUrl("jdbc:h2:./RecipesSpringJdbc/database/hibernate-db-app");

        return managerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPackagesToScan(new String[]{"ru.sbt.javaschool.recipes.springjdbc"});

        factoryBean.setJpaProperties(hibernateProperties());

//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        factoryBean.setJpaVendorAdapter(vendorAdapter);

        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.format_sql", "false");
//        properties.put("ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        return properties;
    }
}