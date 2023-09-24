package com.devcors.javaacademy.lesson6.data.repository;

import com.devcors.javaacademy.lesson6.data.entity.BorrowedCar;
import com.devcors.javaacademy.lesson6.data.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowedCarRepository extends JpaRepository<BorrowedCar, Long> {
    List<BorrowedCar> findByUserId(long userId);

    BorrowedCar findByUserIdAndCarId(Long userId, Long carId);

}
