package ru.sbt.recipes.mvc.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.sbt.recipes.mvc.dataconfig.ApplicationConfigurationWithHibernate;

public class SpringAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                WebConfiguration.class,
                ApplicationConfigurationWithHibernate.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
