package com.devcors.javaacademy.lesson6;

import com.devcors.javaacademy.lesson6.data.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Lesson6Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CarControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllCarsShouldReturnOk() {
        List<Car> cars = webTestClient.get()
                .uri(ub -> ub.path("/cars").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Car.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(cars);
        assertEquals(2, cars.size());
    }

    @Test
    void getCarByIdShouldReturnOk() {
        Car car = webTestClient.get()
                .uri(ub -> ub.path("/cars/1").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Car.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(car);
        assertEquals(1, car.getId());
        assertEquals("BMW", car.getBrand());
    }

    @Test
    void getCarByIdShouldReturnNotFound() {
        webTestClient.get()
                .uri(ub -> ub.path("/cars/3").build())
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
