package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;

import java.io.File;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    File autorizeCourier = new File("src/main/resources/Login/authCourierBody.json");
    File autorizeEmptyFields = new File("src/main/resources/Login/authCourierEmptyBody.json");
    File autorizeInvalid = new File("src/main/resources/Login/authCourierBodyInvalid.json");

    public ValidatableResponse autorizeSuccessful() {
        return given()
                .header("Content-type", "application/json")
                .body(autorizeCourier)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse autorizeEmptyFields() {
        return given()
                .header("Content-type", "application/json")
                .body(autorizeEmptyFields)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse autorizeBroken() {
        return given()
                .header("Content-type", "application/json")
                .body(autorizeInvalid)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

}