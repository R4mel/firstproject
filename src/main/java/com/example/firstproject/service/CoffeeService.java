package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;


    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }


    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }


    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }


    public Coffee create(CoffeeForm coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        if (coffee.getId() != null) {
            return null;
        }
        return coffeeRepository.save(coffee);
    }

    public Coffee update(Long id, CoffeeForm coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        log.info("id: {}, coffee: {}", id, coffee);
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target == null || !id.equals(coffee.getId())) {
            log.info("잘못된 요청! id: {}, coffee: {}", id, coffee);
            return null;
        }
        target.patch(coffee);
        return coffeeRepository.save(target);
    }

    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }
}
