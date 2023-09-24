package com.devcors.javaacademy.lesson6.rest;

import com.devcors.javaacademy.lesson6.data.dto.UserDto;
import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import com.devcors.javaacademy.lesson6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    private void createNewUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @PutMapping("/{id}")
    private void updateUser(@PathVariable Long id, @RequestBody UserDto userDtoUpdate) {
        try {
            userService.updateCar(id, userDtoUpdate);
        } catch (RuntimeException e) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
        }
    }
}
