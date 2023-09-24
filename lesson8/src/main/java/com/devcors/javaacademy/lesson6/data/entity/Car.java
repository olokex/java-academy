package com.devcors.javaacademy.lesson6.data.entity;

import com.devcors.javaacademy.lesson6.data.entity.enums.CarColor;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarType;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="year_")
    private Short year;
    private String brand;
    private String licencePlate;
    @Enumerated(EnumType.STRING)
    private CarType type;
    @Enumerated(EnumType.STRING)
    private CarColor color;
}
