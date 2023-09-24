package com.devcors.javaacademy.lesson6.service;

import com.devcors.javaacademy.lesson6.data.dto.UserDto;
import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User userEntity = user.get();
            return UserDto.builder()
                    .email(userEntity.getEmail())
                    .firstname(userEntity.getFirstname())
                    .lastname(userEntity.getLastname())
                    .address(userEntity.getAddress())
                    .role(userEntity.getRole())
                    .build();
        }
        return null;
    }

    public void save(UserDto userDto) {
        User userEntity = User.builder()
                              .email(userDto.getEmail())
                              .firstname(userDto.getFirstname())
                              .lastname(userDto.getLastname())
                              .address(userDto.getAddress())
                              .role(userDto.getRole())
                              .build();
        userRepository.save(userEntity);
    }

    public void updateCar(Long id, UserDto userDtoUpdate) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException();
        }

        User updatedUser = User.builder()
                               .id(id)
                               .email(userDtoUpdate.getEmail())
                               .firstname(userDtoUpdate.getFirstname())
                               .lastname(userDtoUpdate.getLastname())
                               .address(userDtoUpdate.getAddress())
                               .role(userDtoUpdate.getRole())
                               .build();

        userRepository.save(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }
}
