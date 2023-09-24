package com.devcors.javaacademy.lesson6.data.repository;

import com.devcors.javaacademy.lesson6.data.entity.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrand(String brandName);
}
