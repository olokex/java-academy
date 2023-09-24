package com.devcors.javaacademy.lesson6;

import com.devcors.javaacademy.lesson6.data.entity.BorrowedCar;
import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarColor;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarType;
import com.devcors.javaacademy.lesson6.data.entity.Car;
import com.devcors.javaacademy.lesson6.data.entity.enums.UserRole;
import com.devcors.javaacademy.lesson6.data.repository.BorrowedCarRepository;
import com.devcors.javaacademy.lesson6.data.repository.CarRepository;
import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.CollectionUtils;
import org.springframework.test.web.reactive.server.StatusAssertions;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Lesson7Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class BorrowedCarControllerIntegrationTest {
 @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowedCarRepository borrowedCarRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void afterEach() {
        carRepository.deleteAll();
        userRepository.deleteAll();
        borrowedCarRepository.deleteAll();
    }

    @BeforeEach
    void beforeEach() {
        userRepository.save(User.builder()
                    .email("admin@email.com")
                    .username("admin")
                    .role(UserRole.ADMIN)
                    .password(passwordEncoder.encode("password"))
                    .build());
    }

    private static final String credentials = "admin:password";
    private static final String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

    private static final String LICENCE_PLATE_1 = "4H44444";
    private static final String LICENCE_PLATE_2 = "5H55555";

    private static final String BRAND_1 = "BMW";
    private static final String BRAND_2 = "Audi";

    private static final short YEAR_1 = (short) 1999;
    private static final short YEAR_2 = (short) 2002;

    private static final Car CAR_1 = Car.builder()
            .brand(BRAND_1)
            .year(YEAR_1)
            .licencePlate(LICENCE_PLATE_1)
            .color(CarColor.BLACK)
            .type(CarType.HATCHBACK)
            .build();

    private static final Car CAR_2 = Car.builder()
            .brand(BRAND_2)
            .year(YEAR_2)
            .licencePlate(LICENCE_PLATE_2)
            .color(CarColor.PINK)
            .type(CarType.COMBI)
            .build();

    public static final String EMAIL_1 = "admin@email.com";
    public static final String FIRSTNAME_1 = "Firstname";
    public static final String LASTNAME_1 = "Lastname";
    public static final String ADDRESS_1 = "Downing street 10";
    public static final String EMAIL_2 = "user@email.com";

    private static final User USER_1 = User.builder()
            .email(EMAIL_1)
            .firstname(FIRSTNAME_1)
            .lastname(LASTNAME_1)
            .role(UserRole.ADMIN)
            .address(ADDRESS_1)
            .build();

    private static final User USER_2 = User.builder()
            .email(EMAIL_2)
            .firstname("Firstname")
            .lastname("Lastname")
            .role(UserRole.USER)
            .address("Baker street 10")
            .build();


    @Test
    void borrowCarShouldReturnOk() {
        Car car = carRepository.save(CAR_1);
        User user = userRepository.save(USER_1);

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void returnCarShouldReturnOk() {
        Car car = carRepository.save(CAR_1);
        User user = userRepository.save(USER_1);

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus().isOk();

        webTestClient.put()
                .uri("/users/{userId}/car/return/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void returnCarMultipleRecords() {
        Car car = carRepository.save(CAR_1);
        Car car_2 = carRepository.save(CAR_2);

        User user = userRepository.save(USER_1);

        BorrowedCar borrowedCarRecord = new BorrowedCar();
        borrowedCarRecord.setCarId(car.getId());
        borrowedCarRecord.setUserId(user.getId());

        BorrowedCar borrowedCarRecord_2 = new BorrowedCar();
        borrowedCarRecord.setCarId(car_2.getId());
        borrowedCarRecord.setUserId(user.getId());

        borrowedCarRepository.save(borrowedCarRecord);
        borrowedCarRepository.save(borrowedCarRecord_2);

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus().isOk();

        webTestClient.put()
                .uri("/users/{userId}/car/return/{carId}", user.getId(), car_2.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.put()
                .uri("/users/{userId}/car/return/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk();

        assertEquals(1, borrowedCarRepository.findAll().size());
    }

    @Test
    void getBorrowedCarsShouldReturnOkWithCarList() {
        Car car = carRepository.save(CAR_1);
        Car car_2 = carRepository.save(CAR_2);

        User user = userRepository.save(USER_1);

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car_2.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri("/users/{userId}/car", user.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Car.class)
                .hasSize(2);
    }

    @Test
    void insertAlreadyBorrowedShouldReturnNotAllowed() {
        Car car = carRepository.save(CAR_1);
        User user = userRepository.save(USER_1);

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus().isOk();

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus().is4xxClientError();

    }

    @Test
    void userDoesNotExistShouldReturnNotFound() {
        User user = userRepository.save(USER_1);
        Car car = carRepository.save(CAR_1);

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId(), car.getId() + 1L)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void carDoesNotExistShouldReturnNotFound() {
        User user = userRepository.save(USER_1);
        Car car = carRepository.save(CAR_1);

        webTestClient.put()
                .uri("/users/{userId}/car/borrow/{carId}", user.getId() + 1L, car.getId())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
