package com.devcors.javaacademy.gpsapplication.GpsApplication.data.repository;

import com.devcors.javaacademy.gpsapplication.GpsApplication.data.entity.CarLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarLocationRepository extends JpaRepository<CarLocation, Long> {

    List<CarLocation> findAllByCarId(Long carId);
}
