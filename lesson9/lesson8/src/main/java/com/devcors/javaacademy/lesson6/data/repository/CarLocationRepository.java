package com.devcors.javaacademy.lesson6.data.repository;

import com.devcors.javaacademy.lesson6.data.entity.CarLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarLocationRepository extends JpaRepository<CarLocation, Long> {

}