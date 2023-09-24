package com.devcors.javaacademy.lesson6;

import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarColor;
import com.devcors.javaacademy.lesson6.data.entity.enums.CarType;
import com.devcors.javaacademy.lesson6.data.entity.Car;
import com.devcors.javaacademy.lesson6.data.entity.enums.UserRole;
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

import javax.annotation.PostConstruct;
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
class CarControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void afterEach() {
        carRepository.deleteAll();
        userRepository.deleteAll();
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

    private static final String credentials = "admin:password";
    private static final String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

    @Test
    void createCarShouldReturnOk() {
        webTestClient.post()
                .uri(ub -> ub.path("/cars").build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .bodyValue(CAR_1)
                .exchange()
                .expectStatus()
                .isOk();

        List<Car> cars = carRepository.findAll();
        assertFalse(CollectionUtils.isEmpty(cars));
        assertEquals(1, cars.size());
        Car car = cars.get(0);

        assertEquals(BRAND_1, car.getBrand());
        assertEquals(YEAR_1, car.getYear());
        assertEquals(LICENCE_PLATE_1, car.getLicencePlate());
        assertEquals(CarColor.BLACK, car.getColor());
        assertEquals(CarType.HATCHBACK, car.getType());
    }

    @Test
    void updateCarShouldReturnOk() {
        Car savedCar = carRepository.save(CAR_1);
        Long carId = savedCar.getId();

        savedCar.setId(null);
        savedCar.setBrand(BRAND_2);
        webTestClient.put()
                .uri(ub -> ub.path("/cars/" + carId).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .bodyValue(savedCar)
                .exchange()
                .expectStatus()
                .isOk();

        Optional<Car> car = carRepository.findById(carId);
        assertTrue(car.isPresent());
        assertEquals(BRAND_2, car.get().getBrand());
    }

    @Test
    void updateCarByIdShouldReturnBadRequest() {
        Car savedCar = carRepository.save(CAR_1);

        webTestClient.put()
                .uri(ub -> ub.path("/cars/" + (savedCar.getId() + 1)).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .bodyValue(savedCar)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void getAllCarsShouldReturnOk() {
        carRepository.save(CAR_1);
        carRepository.save(CAR_2);

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
        Car savedCar = carRepository.save(CAR_1);

        System.out.println(userRepository.findAll());

        Car car = webTestClient.get()
                .uri(ub -> ub.path("/cars/" + savedCar.getId()).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Car.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(car);
        assertEquals(BRAND_1, car.getBrand());
    }

    @Test
    void getCarsByBrandShouldReturnOk() {
        carRepository.save(CAR_1);
        carRepository.save(CAR_2);

        List<Car> cars = webTestClient.get()
                .uri(ub -> ub.path("/cars/filter").queryParam("brandName", BRAND_1).build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Car.class)
                .returnResult()
                .getResponseBody();

        System.out.println(cars);
        assertFalse(CollectionUtils.isEmpty(cars));
        assertEquals(1, cars.size());
        assertEquals(LICENCE_PLATE_1, cars.get(0).getLicencePlate());
    }

    @Test
    void getCarByIdShouldReturnNotFound() {
        Car savedCar = carRepository.save(CAR_1);

        webTestClient.get()
                .uri(ub -> ub.path("/cars/" + (savedCar.getId() + 1)).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void deleteCarShouldReturnOk() {
        Car savedCar = carRepository.save(CAR_1);
        carRepository.save(CAR_2);

        webTestClient.delete()
                .uri(ub -> ub.path("/cars/" + savedCar.getId()).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk();

        List<Car> cars = carRepository.findAll();
        assertFalse(CollectionUtils.isEmpty(cars));
        assertEquals(1, cars.size());
        assertEquals(LICENCE_PLATE_2, cars.get(0).getLicencePlate());
    }
}
