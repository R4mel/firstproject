package com.example.firstproject.api;

import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoffeeApiController {
    private CoffeeRepository coffeeRepository;

    public CoffeeApiController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @GetMapping("/api/coffees")
    List<Coffee> index() {
        return coffeeRepository.findAll();
    }
}
