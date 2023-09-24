package com.devcors.javaacademy.lesson6.data.dto;

import com.devcors.javaacademy.lesson6.data.entity.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private UserRole role;
}
