package com.crm.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoLoader implements CommandLineRunner{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Autowired
    public ContactRepository repository;

    @Autowired
    //public DemoLoader() {
    public DemoLoader(ContactRepository repository) {
        logger.debug("******** Instantiate DemoLoader");
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new Contact("Daniel", "Monteiro", "dsuruagy@gmail.com"));

    }
    
}
