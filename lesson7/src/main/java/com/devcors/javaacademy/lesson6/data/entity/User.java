package com.devcors.javaacademy.lesson6.data.entity;

import com.devcors.javaacademy.lesson6.data.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private UserRole role;

}
