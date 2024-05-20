package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CoffeeApiController {
    private final CoffeeService coffeeService;

    public CoffeeApiController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/api/coffees")
    public ResponseEntity<List<Coffee>> index() {
        List<Coffee> coffeeList = coffeeService.index();
        if (coffeeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(coffeeList);
    }

    @GetMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> show(@PathVariable Long id) {
        Coffee coffee = coffeeService.show(id);
        return (coffee != null) ? ResponseEntity.status(HttpStatus.OK).body(coffee) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeForm coffeeDto) {
        Coffee coffee = coffeeService.create(coffeeDto);
        return (coffee != null) ? ResponseEntity.status(HttpStatus.OK).body(coffee) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeForm coffeeDto) {
        Coffee coffee = coffeeService.update(id, coffeeDto);
        return(coffee != null) ? ResponseEntity.status(HttpStatus.OK).body(coffee) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        Coffee coffee = coffeeService.delete(id);
        return(coffee != null) ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(coffee) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
