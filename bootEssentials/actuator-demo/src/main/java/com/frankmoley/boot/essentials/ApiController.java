package com.frankmoley.boot.essentials;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    private final PresidentRepository presidentRepository;
    private final Counter greetingCounter;

    public ApiController(PresidentRepository presidentRepository, MeterRegistry registry){
        this.presidentRepository = presidentRepository;
        this.greetingCounter = Counter.builder("api.greeting").register(registry);
    }

    @GetMapping("greeting")
    public String getGreeting(){
        greetingCounter.increment();
        return "Hello LinkedIn Learning Student";
    }

    @GetMapping("presidents")
    @Timed(value = "api.getAllPresidents")
    public List<President> getAllUSPresidents(){
        List<President> presidents = new ArrayList<>();
        this.presidentRepository.findAll().forEach(presidents::add);
        return presidents;
    }

}
