package com.devcors.javaacademy.lesson6.rest;


import com.devcors.javaacademy.lesson6.data.dto.CarDto;
import com.devcors.javaacademy.lesson6.data.entity.BorrowedCar;
import com.devcors.javaacademy.lesson6.data.entity.Car;
import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.repository.BorrowedCarRepository;
import com.devcors.javaacademy.lesson6.data.repository.CarRepository;
import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import com.devcors.javaacademy.lesson6.service.BorrowedCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/car")
public class BorrowedCarController {

    @Autowired
    private BorrowedCarService borrowedCarService;

    @PutMapping("/borrow/{carId}")
    private ResponseEntity<String> borrowCar(@PathVariable long userId, @PathVariable long carId) {
         return borrowedCarService.borrowCar(userId, carId);
    }

    @PutMapping("/return/{carId}")
    private void returnCar(@PathVariable long userId, @PathVariable long carId) {
        borrowedCarService.returnCar(userId, carId);
    }

    @GetMapping
    private List<CarDto> getBorrowedCars(@PathVariable long userId) {
        return borrowedCarService.getBorrowedCarsByUserId(userId);
    }
}



