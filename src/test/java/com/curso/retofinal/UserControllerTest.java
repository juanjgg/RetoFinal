package com.curso.retofinal;

import com.curso.retofinal.domain.User;
import com.curso.retofinal.domain.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final AtomicLong userId = new AtomicLong();

    @AfterAll
    public static void tearDown(@Autowired UserRepository repository){
        repository.deleteAll().block();
    }


    @Test
    @Order(1)
    void createUser() {
        User userTest = new User();
        userTest.setUserId(99L);
        userTest.setName("Juan Jose Gutierrez");
        userTest.setBalance(10000.0);

        var newUser = webTestClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userTest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();
        userId.set(Objects.requireNonNull(newUser).getUserId());
    }

    @Test
    @Order(2)
    void getUserById() {
        webTestClient
                .get()
                .uri("/users/{id}",userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Juan Jose Gutierrez")
                .jsonPath("$.balance").isEqualTo("10000.0");
    }

    @Test
    @Order(3)
    void updateUserBalance() {
        Map<String,Double> body = new HashMap<>();
        body.put("amount", (double) 100.00);

        System.out.println("body: " + body.get("amount"));
        webTestClient
                .put()
                .uri("/users/{id}/balance",userId)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Juan Jose Gutierrez")
                .jsonPath("$.balance").isEqualTo("10100.0");
    }
}