package com.devcors.javaacademy.lesson6.data.entity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
public class BorrowedCar {

    @Id
    @GeneratedValue
    private Long Id;
    private Long carId;
    private Long userId;

}
