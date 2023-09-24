package com.devcors.javaacademy.lesson6.rest;

import com.devcors.javaacademy.lesson6.data.dto.CarDto;
import com.devcors.javaacademy.lesson6.data.entity.Car;
import com.devcors.javaacademy.lesson6.data.repository.CarRepository;
import com.devcors.javaacademy.lesson6.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    private List<CarDto> getCars() {
        return carService.getAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        try {
            CarDto car = carService.findById(id);
            if (car == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(car);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createCar(@RequestBody CarDto carDto) {
        try {
            carService.createCar(carDto);
            return ResponseEntity.ok("Car created successfully");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Car data is not valid");
        }
    }

    @PutMapping("/{id}")
    private void updateCar(@PathVariable Long id, @RequestBody CarDto carUpdate) {
        try {
            carService.updateCar(id, carUpdate);
        } catch (RuntimeException e) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteCar(@PathVariable Long id) {
        try {
            carService.deleteCar(id);
            return ResponseEntity.ok("Car deleted successfully");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car with ID " + id + " not found");
        }
    }

    @GetMapping("/filter")
    private List<CarDto> getCarsByBrandName(@RequestParam("brandName") String brandName) {
        return carService.findByBrand(brandName);
    }

}



