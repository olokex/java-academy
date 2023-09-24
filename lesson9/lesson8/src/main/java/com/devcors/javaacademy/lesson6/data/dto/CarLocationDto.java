package com.devcors.javaacademy.lesson6.data.dto;

import lombok.Data;

@Data
public class CarLocationDto {
    private Long carId;
    private Double longitude;
    private Double altitude;
}