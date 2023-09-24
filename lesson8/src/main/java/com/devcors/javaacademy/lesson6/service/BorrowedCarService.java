package com.devcors.javaacademy.lesson6.service;

import com.devcors.javaacademy.lesson6.data.dto.BorrowedCarDto;
import com.devcors.javaacademy.lesson6.data.dto.CarDto;
import com.devcors.javaacademy.lesson6.data.entity.BorrowedCar;
import com.devcors.javaacademy.lesson6.data.entity.Car;
import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.repository.BorrowedCarRepository;
import com.devcors.javaacademy.lesson6.data.repository.CarRepository;
import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowedCarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BorrowedCarRepository borrowedCarRepository;

    public void returnCar(Long userId, Long carId) {
        BorrowedCar borrowedCarId = borrowedCarRepository.findByUserIdAndCarId(userId, carId);
        if (borrowedCarId != null) {
            borrowedCarRepository.deleteById(borrowedCarId.getId());
        }
    }

    public ResponseEntity<String> borrowCar(long userId, long carId) {
        Optional<Car> car = carRepository.findById(carId);
        Optional<User> user = userRepository.findById(userId);

        if (car.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car does NOT exist");
        }

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does NOT exist");
        }

        BorrowedCar borrowedCarId = borrowedCarRepository.findByUserIdAndCarId(userId, carId);

        if (borrowedCarId != null) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Already stored");
        }

        BorrowedCar borrowedCar = new BorrowedCar();
        borrowedCar.setCarId(carId);
        borrowedCar.setUserId(userId);
        borrowedCarRepository.save(borrowedCar);

        return ResponseEntity.ok("Stored successfully");
    }

    public List<CarDto> getBorrowedCarsByUserId(long userId) {
        List<BorrowedCar> borrowedCars = borrowedCarRepository.findByUserId(userId);
        List<Long> carIds = borrowedCars.stream()
                .map(BorrowedCar::getCarId)
                .collect(Collectors.toList());

        List<Car> cars = carRepository.findAllById(carIds);

        return cars.stream()
                .map(CarService::convertToDto)
                .collect(Collectors.toList());
    }
}
