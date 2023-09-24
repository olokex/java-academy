package com.devcors.javaacademy.gpsapplication.GpsApplication.data.dto;

import lombok.Data;

@Data
public class CarLocationDto {
    private Long carId;
    private Double longitude;
    private Double latitude;
}


