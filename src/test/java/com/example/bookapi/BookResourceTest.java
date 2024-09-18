package com.example.bookapi;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookResourceTest {
    @Test
    @Order(1)
    public void testCreateBook() {
        given()
                .body("{\"title\":\"Test Book\", \"author\":\"Test Author\", \"publicationYear\":2021, \"genre\":\"Fiction\", \"isbn\":\"1234567890123\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/books")
                .then()
                .statusCode(201)
                .body("title", is("Test Book"))
                .body("author", is("Test Author"))
                .body("publicationYear", is(2021))
                .body("genre", is("Fiction"))
                .body("isbn", is("1234567890123"));
    }

    @Test
    public void testGetAllBooks() {
        RestAssured.when().get("/books")
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.is(1));
    }

    @Test
    public void testInvalidISBN() {
        given()
                .body("{\"title\":\"Invalid ISBN\", \"author\":\"Test Author\", \"publicationYear\":2021, \"genre\":\"Fiction\", \"isbn\":\"123\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/books")
                .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("ISBN must contain exactly 13 characters"));
    }

    @Test
    public void testMissingGenre() {
        given()
                .body("{\"title\":\"Missing Genre\", \"author\":\"Test Author\", \"publicationYear\":2021, \"isbn\":\"1234567890123\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/books")
                .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Genre is required"));
    }

    @Test
    public void testUpdateBook() {
        given()
                .body("{\"title\":\"Updated Book\", \"author\":\"Updated Author\", \"publicationYear\":2022, \"genre\":\"Non-Fiction\", \"isbn\":\"9876543210123\"}")
                .header("Content-Type", "application/json")
                .when()
                .put("/books/1")
                .then()
                .statusCode(200)
                .body("title", is("Updated Book"))
                .body("author", is("Updated Author"))
                .body("publicationYear", is(2022))
                .body("genre", is("Non-Fiction"))
                .body("isbn", is("9876543210123"));
    }

    @Test
    public void testDeleteBook() {
        RestAssured.when().delete("/books/1")
                .then()
                .statusCode(204);
    }

}