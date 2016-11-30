package ru.sbt.recipes.mvc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by скурихин on 26.11.2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.sbt.recipes.mvc.web")
public class WebConfiguration extends WebMvcConfigurerAdapter {

//    private static final String RESOURCES_HANDLER = "/resources/";
//    private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
//    }
//
//    @Controller
//    static class FaviconController {
//        @RequestMapping("favicon.ico")
//        String favicon() {
//            return "forward:/resources/images/favicon.ico";
//        }
//    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        return new InternalResourceViewResolver("/WEB-INF/pages/", ".jsp");
    }
}
