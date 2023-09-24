package com.devcors.javaacademy.lesson6.data.dto;

import com.devcors.javaacademy.lesson6.data.entity.enums.CarColor;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    private Short year;
    private String brand;
    private String licencePlate;
    private CarType type;
    private CarColor color;
}
