package com.devcors.javaacademy.lesson6.data.entity;

import com.devcors.javaacademy.lesson6.data.entity.enums.CarColor;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Integer id;
    private Short year;
    private String brand;
    private String licencePlate;
    private CarType type;
    private CarColor color;

}
