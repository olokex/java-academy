package com.devcors.javaacademy.lesson6;

import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.entity.enums.UserRole;
import org.junit.jupiter.api.Assertions;
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
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllUsersShouldReturnOk() {
        List<User> users = webTestClient.get()
                .uri(ub -> ub.path("/users").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void getUserByIdShouldReturnOk() {
        User admin = webTestClient.get()
                .uri(ub -> ub.path("/users/1").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(admin);
        assertEquals("admin@email.com", admin.getEmail());
        Assertions.assertEquals(UserRole.ADMIN, admin.getRole());

        User user = webTestClient.get()
                .uri(ub -> ub.path("/users/2").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(user);
        assertEquals("user@email.com", user.getEmail());
        Assertions.assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    void getUserByIdShouldReturnNotFound() {
        webTestClient.get()
                .uri(ub -> ub.path("/users/3").build())
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
