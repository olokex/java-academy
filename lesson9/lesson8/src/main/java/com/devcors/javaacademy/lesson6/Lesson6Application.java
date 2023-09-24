package com.devcors.javaacademy.lesson6;

//import com.devcors.javaacademy.lesson6.data.repository.CarRepository;
//import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class Lesson6Application {

    /**
     * <strong>Homework 6</strong>
     * <p>Create {@link com.devcors.lesson6.rest.CarController} and {@link com.devcors.lesson6.rest.UserController}
     * <p>{@link com.devcors.lesson6.rest.CarController} will contains two methods {@link com.devcors.lesson6.rest.CarController#getCars()} and {@link com.devcors.lesson6.rest.CarController#getCar(Integer)}
     * <p><strong>/cars</strong> endpoint {@link com.devcors.lesson6.rest.CarController#getCars()} returns 200 http status code and all cars as response body from {@link CarRepository}
     * <p><strong>/cars/{id}</strong> endpoint {@link com.devcors.lesson6.rest.CarController#getCar(Integer)} returns 200 http status code and car as response body when cars was found in {@link CarRepository}, if there is no car with this id in repository then return 404 http status code and no response body
     * <p>{@link com.devcors.lesson6.rest.UserController} will contains two methods {@link com.devcors.lesson6.rest.UserController#getUsers()} and {@link com.devcors.lesson6.rest.UserController#getUser(Long)}
     * <p><strong>/users</strong> endpoint {@link com.devcors.lesson6.rest.UserController#getUsers()} ()} returns 200 http status code and all users as response body from {@link UserRepository}
     * <p><strong>/users/{id}</strong> endpoint {@link com.devcors.lesson6.rest.UserController#getUser(Long)} returns 200 http status code and user as response body when user was found in {@link UserRepository}, if there is no user with this id in repository then return 404 http status code and no response body
     * <strong>BONUS:</strong> use service layer with {@link Service} annotation
     * <p>To test your implementation, you can use CarControllerIntegrationTest and UserControllerIntegrationTest,
     * which starts application as a spring boot application and try to reach your REST endpoints.
     * <p>This application should also be startable as a Spring boot application.
     * <p>Note: do not change implementation of existing repository methods.
     * <p>And remember - google is your friend :)
     */
    public static void main(String[] args) {
        SpringApplication.run(Lesson6Application.class, args);
    }

}
