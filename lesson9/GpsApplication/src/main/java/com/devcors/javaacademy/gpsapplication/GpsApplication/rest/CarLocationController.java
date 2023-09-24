package com.devcors.javaacademy.gpsapplication.GpsApplication.rest;

import com.devcors.javaacademy.gpsapplication.GpsApplication.data.dto.CarLocationDto;
import com.devcors.javaacademy.gpsapplication.GpsApplication.service.CarLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CarLocationController {

    @Autowired
    private CarLocationService carLocationService;

    @GetMapping("/gps/{carId}")
    public ResponseEntity<List<CarLocationDto>> getCarLocations(@PathVariable Long carId) {
        List<CarLocationDto> carLocations = carLocationService.getCarLocations(carId);
        return ResponseEntity.ok(carLocations);
    }
}
