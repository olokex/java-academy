package com.devcors.javaacademy.lesson6.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowedCarDto {
    private Long carId;
    private Long userId;
}
