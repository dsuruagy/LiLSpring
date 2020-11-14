package com.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Class that will hold all the configuration for your application with respect to the spring MVC
 * features that you want to use as a part of this application.
 */
@Configuration
@ComponentScan(basePackages = "com.test")
public class ApplicationConfig extends WebMvcConfigurationSupport {

    /**
     * This method helps us to tell springmvc that whenever you're picking up the static files, like
     * css or images, you have to pick it up from a particular part. Now understand this point: the
     * project structure that you're looking at on the left side of IDE is going to be a little different
     * from the structure that actually gets generated in the war package. So we need to tell
     * springframework that when the war is created, you need to pick up the css and images from the
     * appropriate parts.
     * @param registry registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**", "images/**")
                .addResourceLocations("classpath:/css/", "classpath:/images/");
        super.addResourceHandlers(registry);
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }
}
