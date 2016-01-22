package com.example.config;

import com.example.web.HomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimwonyong on 2016. 1. 22..
 */
@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = {"com.example"}, useDefaultFilters = false,
        includeFilters = { @ComponentScan.Filter(Controller.class),
                @ComponentScan.Filter(ControllerAdvice.class)
        },

        excludeFilters = {  @ComponentScan.Filter(Service.class),
                @ComponentScan.Filter(Repository.class),
                @ComponentScan.Filter(Component.class)
        }
)
public class WebAppConfig  extends WebMvcConfigurerAdapter {

    // in test, after comment 2016.01.22
    @Bean
    public HomeController commonController(){
        return new HomeController();
    }

    // addResourceHandlers
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/").addResourceLocations("/resources/**");
    }

    // configureDefaultServlethandling
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // internalResourceViewResolver
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setOrder(2);
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    // viewResolver
    @Bean
    public ContentNegotiatingViewResolver viewResolver() throws Exception {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setOrder(1);
        resolver.setDefaultViews(getViewList());
        return resolver;
    }

        // viewResolver getViewList
        private List<View> getViewList() throws Exception {
            List<View> list = new ArrayList<View>();
            list.add(new MappingJackson2JsonView());
            return list;
        }
}
