package com.test.hplus.config;

import com.test.hplus.converter.StringToGenderConverter;
import com.test.hplus.interceptors.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.XmlViewResolver;

@Configuration
@ComponentScan(basePackages = "com.test.hplus")
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
                .addResourceLocations("classpath:/static/css/", "classpath:/static/images/");
    }


    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(7000);
        configurer.setTaskExecutor(mvcTaskExecutor());
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // This interceptor works for all paths, but could be specific to /login, for example.
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/*");
        // Interceptor to change themes based on URL parameters
        registry.addInterceptor(new ThemeChangeInterceptor());
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToGenderConverter());
    }

    @Bean
    public ThemeResolver themeResolver() {
        CookieThemeResolver cookieThemeResolver = new CookieThemeResolver();
        cookieThemeResolver.setCookieName("theme");
        cookieThemeResolver.setDefaultThemeName("client-theme1");
        return cookieThemeResolver;
    }

    @Bean
    public AsyncTaskExecutor mvcTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("hplusapp-thread-");
        return threadPoolTaskExecutor;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setOrder(2); // Second in the chain
        return viewResolver;
    }

    @Bean
    public XmlViewResolver xmlViewResolver() {
        XmlViewResolver viewResolver = new XmlViewResolver();
        viewResolver.setLocation(new ClassPathResource("views.xml"));
        viewResolver.setOrder(1); // First in the chain
        return viewResolver;
    }
    /*    @Bean
        public ResourceBundleViewResolver resourceBundleViewResolver() {
            ResourceBundleViewResolver resourceBundleViewResolver = new ResourceBundleViewResolver();
            resourceBundleViewResolver.setBasename("views");
            return resourceBundleViewResolver;
        }*/
}
