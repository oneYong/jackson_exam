package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by kimwonyong on 2016. 1. 22..
 */
@Configuration
public class WebXmlConfig implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
        setDispatcherServlet(servletContext);

        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("CharacterEncodingFilter", characterEncodingFilter());
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        setHiddenHttpMethodFilter(servletContext);
    }

    private void setDispatcherServlet(ServletContext servletContext) {
        WebApplicationContext context = this.getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.example");
        return context;
    }

    // utf-8 encoding config
    private CharacterEncodingFilter characterEncodingFilter() {

        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }
    // hiddenHttpMethodFilter config
    private void setHiddenHttpMethodFilter(ServletContext servletContext) {
        servletContext.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class).addMappingForUrlPatterns(null, false, "/*");
    }
}
