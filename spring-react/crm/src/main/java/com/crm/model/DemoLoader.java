package com.crm.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoLoader implements CommandLineRunner{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ContactRepository repository;

    public DemoLoader() {
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("******** Instantiate DemoLoader");
        repository.save(new Contact("Daniel", "Monteiro", "dsuruagy@gmail.com"));

    }
    
}
