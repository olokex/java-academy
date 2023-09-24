package com.devcors.javaacademy.gpsapplication.GpsApplication.service;

import com.devcors.javaacademy.gpsapplication.GpsApplication.data.dto.CarLocationDto;
import com.devcors.javaacademy.gpsapplication.GpsApplication.data.entity.CarLocation;
import com.devcors.javaacademy.gpsapplication.GpsApplication.data.repository.CarLocationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarLocationService {

    @Autowired
    private CarLocationRepository carLocationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<CarLocationDto> getCarLocations(Long carId) {
        List<CarLocation> carLocations = carLocationRepository.findAllByCarId(carId);
        List<CarLocationDto> carLocationDtos = new ArrayList<>();

        for (CarLocation carLocation : carLocations) {
            CarLocationDto carLocationDto = objectMapper.convertValue(carLocation, CarLocationDto.class);
            carLocationDtos.add(carLocationDto);
        }

        return carLocationDtos;
    }
}
