package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    File jsonValid = new File("src/main/resources/Courier/newCourierBody.json");
    File jsonInvalid = new File("src/main/resources/Courier/newCourierBodyInvalid.json");
    File jsonEmpty = new File("src/main/resources/Courier/newCourierBodyEmpty.json");
    File jsonLogin = new File("src/main/resources/Courier/loginCourierBody.json");


    public ValidatableResponse creatCourierValid() {
        return given()
                .header("Content-type", "application/json")
                .body(jsonValid)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse creatCourierInvalid() {
        return given()
                .header("Content-type", "application/json")
                .body(jsonInvalid)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse creatCourierEmpty() {
        return given()
                .header("Content-type", "application/json")
                .body(jsonEmpty)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse loginCourier() {
        return given()
                .header("Content-type", "application/json")
                .body(jsonLogin)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }

}