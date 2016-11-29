package ru.sbt.recipes.mvc.dataconfig;

//import org.hibernate.ejb.HibernatePersistence;

import liquibase.integration.spring.SpringLiquibase;
//import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"ru.sbt.recipes.mvc.service",
        "ru.sbt.recipes.mvc.dataconfig"//,
//        "ru.sbt.recipes.mvc.entity",
     /*   "ru.sbt.recipes.mvc.repository"*/})

//@ComponentScan(basePackages = {"ru.sbt.recipes.mvc"}, excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = IngredientDaoImpl.class),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RecipeDaoImpl.class),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RecipesToIngredientsDaoImpl.class)})
@EnableJpaRepositories("ru.sbt.recipes.mvc.repository")
@EnableTransactionManagement
public class ApplicationConfigurationWithHibernate {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource managerDataSource = new DriverManagerDataSource();
        managerDataSource.setDriverClassName("org.h2.Driver");
        managerDataSource.setUrl("jdbc:h2:./database/app");

        return managerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[]{"ru.sbt.recipes.mvc.entity"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        factoryBean.setJpaProperties(hibernateProperties());
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

//    @Bean
//    public SpringLiquibase liquibase(DataSource dataSource) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//
//        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog("classpath:liquibase.xml");
//
//        return liquibase;
//    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
//        properties.put("hibernate.hbm2ddl.auto", "drop");
//        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
//        properties.put("ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

        properties.put("hibernate.connection.charSet","UTF-8");

        properties.put("hibernate.connection.characterEncoding","UTF-8");

        return properties;
    }
}