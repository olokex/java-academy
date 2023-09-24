package com.devcors.javaacademy.lesson6;

import com.devcors.javaacademy.lesson6.data.entity.enums.UserRole;
import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
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
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PostConstruct
    void init() {
        userRepository.save(User.builder()
                    .email("admin@email.com")
                    .username("admin")
                    .role(UserRole.ADMIN)
                    .password(passwordEncoder.encode("password"))
                    .build());
    }
    private static final String credentials = "admin:password";
    private static final String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void createUserShouldReturnOk() {
        webTestClient.post()
                .uri(ub -> ub.path("/users").build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .bodyValue(USER_1)
                .exchange()
                .expectStatus()
                .isOk();

        List<User> users = userRepository.findAll();
        assertFalse(CollectionUtils.isEmpty(users));
        assertEquals(2, users.size());
        User user = users.get(1);

        assertEquals(EMAIL_1, user.getEmail());
        assertEquals(FIRSTNAME_1, user.getFirstname());
        assertEquals(LASTNAME_1, user.getLastname());
        assertEquals(UserRole.ADMIN, user.getRole());
        assertEquals(ADDRESS_1, user.getAddress());
    }

    @Test
    void updateUserShouldReturnOk() {
        User savedUser = userRepository.save(USER_1);
        Long userId = savedUser.getId();

        savedUser.setId(null);
        savedUser.setRole(UserRole.USER);
        webTestClient.put()
                .uri(ub -> ub.path("/users/" + userId).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .bodyValue(savedUser)
                .exchange()
                .expectStatus()
                .isOk();

        Optional<User> user = userRepository.findById(userId);
        assertTrue(user.isPresent());
        assertEquals(UserRole.USER, user.get().getRole());
    }

    @Test
    void updateUserByIdShouldReturnBadRequest() {
        User savedUser = userRepository.save(USER_1);

        webTestClient.put()
                .uri(ub -> ub.path("/users/" + (savedUser.getId() + 1)).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .bodyValue(savedUser)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void getAllUsersShouldReturnOk() {
        userRepository.save(USER_1);
        userRepository.save(USER_2);

        List<User> users = webTestClient.get()
        .uri(ub -> ub.path("/users").build())
        .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(User.class)
        .returnResult()
        .getResponseBody();

        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    void getUserByIdShouldReturnOk() {
        User savedUser = userRepository.save(USER_1);

        User user = webTestClient.get()
                .uri(ub -> ub.path("/users/" + savedUser.getId()).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(user);
        assertEquals(ADDRESS_1, user.getAddress());
    }

    @Test
    void getUserByIdShouldReturnNotFound() {
        User savedUser = userRepository.save(USER_1);

        webTestClient.get()
                .uri(ub -> ub.path("/users/" + (savedUser.getId() + 1L)).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void deleteUserShouldReturnOk() {
        User savedUser = userRepository.save(USER_1);
        userRepository.save(USER_2);

        webTestClient.delete()
                .uri(ub -> ub.path("/users/" + savedUser.getId()).build())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .exchange()
                .expectStatus()
                .isOk();

        List<User> users = userRepository.findAll();
        assertFalse(CollectionUtils.isEmpty(users));
        assertEquals(2, users.size());
        assertEquals(EMAIL_2, users.get(1).getEmail());
    }
}
