package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersSteps {

    public ValidatableResponse getArrayOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders")
                .then();
    }

}