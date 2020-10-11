package com.frankmoley.boot.essentials;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    private final PresidentRepository presidentRepository;

    public ApiController(PresidentRepository presidentRepository){
        this.presidentRepository = presidentRepository;
    }

    @GetMapping("greeting")
    public String getGreeting(){
        return "Hello LinkedIn Learning Student";
    }

    @GetMapping("presidents")
    public List<President> getAllUSPresidents(){
        List<President> presidents = new ArrayList<>();
        this.presidentRepository.findAll().forEach(presidents::add);
        return presidents;
    }

}
