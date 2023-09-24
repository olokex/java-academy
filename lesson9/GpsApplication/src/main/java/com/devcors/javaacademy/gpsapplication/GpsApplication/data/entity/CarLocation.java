package com.devcors.javaacademy.gpsapplication.GpsApplication.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class CarLocation {
    @Id
    @GeneratedValue
    private Long id;
    private Long carId;
    private Double longitude;
    private Double latitude;
}