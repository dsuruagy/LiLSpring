package com.frankmoley.lil.fid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OutputService {
    @Value("${app.name}")
    private String name;

    private final GreetingService greetingService;
    private final TimeService timeService;

    /**
     * Not default constructor used to instantiate OutputService, that requires two arguments.
     * As there is no default constructor, the Autowired annotation is optional here, because
     * Spring knows that it should auto wire dependencies to create a new instance.
     *
     * It was used to make it more explicit here.
     * @param greetingService
     * @param timeService
     */
    @Autowired
    public OutputService(GreetingService greetingService, TimeService timeService){
        this.greetingService = greetingService;
        this.timeService = timeService;
    }

    public void generateOutput(){
        String output = timeService.getCurrentTime() + " " + greetingService.getGreeting(name);
        System.out.println(output);
    }

}
