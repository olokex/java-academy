package com.devcors.javaacademy.lesson6.rest;

import com.devcors.javaacademy.lesson6.data.entity.Car;
import com.devcors.javaacademy.lesson6.data.repository.impl.CarRepositoryImpl;
import com.devcors.javaacademy.lesson6.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars/{id}")
    public ResponseEntity<Optional<Car>> getCar(@PathVariable Integer id) {
        Optional<Car> car = carService.getCarById(id);
        if (car.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars() {
        List<Car> cars = carService.getAllCars();
        if (cars == null || cars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cars);
    }
}
