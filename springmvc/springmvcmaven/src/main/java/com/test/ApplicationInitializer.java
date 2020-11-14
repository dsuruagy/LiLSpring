package com.test;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {
    /**
     * This configuration that we're going to write inside the onStartup is equivalent to the
     * configuration that you would probably do inside a web XML file.
     *
     * @param servletContext ServletContext
     */
    @Override
    public void onStartup(ServletContext servletContext)  {
        /*
         * Bootstrap dispatcherServlet
         * Generally, inside a spring project, the entire configuration is fetched into a context
         * object. Now since this is a web application, and we are using annotations, the API that
         * we are trying to grab is the AnnotationConfigWebApplicationContext.
         */
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        /*
         * We have to tie up the springConfiguration class with the AnnotationConfigWebApplicationContext.
         */
        context.register(ApplicationConfig.class);

        ServletRegistration.Dynamic servletRegistration =
                servletContext.addServlet("mvc", new DispatcherServlet(context));

        /*
         *  Whenever you register a servlet inside your application, you need to tell the container
         *  that this servlet has to be instantiated and initialized on priority. This LoadOnStartup
         *  number is going to help the container do that. The default value for this is minus one,
         *  and if it is set to minus one, then the container can initialize this as a lazy loading
         *  procedure, but we don't want that. So we set that priority to one.
         */
        servletRegistration.setLoadOnStartup(1);

        /*
         * We have already seen in the architecture that every request that
         * comes will come with a URL pattern. Now we want to specify all the requests coming to this
         * project, or coming to this application with a specific URL pattern. So that URL pattern, you
         * define with the addMapping API. It's completely your choice.
         */
        servletRegistration.addMapping("/");
    }
}
