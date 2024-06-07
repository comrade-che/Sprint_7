package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderParamsSteps {

    public ValidatableResponse creatOrderParamColor(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        return given()
                .header("Content-type", "application/json")
                .body("{\n" +
                            "   \"firstName\": \"" + firstName + "\",\n" +
                            "   \"lastName\": \"" + lastName + "\",\n" +
                            "   \"address\": \"" + address + "\",\n" +
                            "   \"metroStation\": " + metroStation + ",\n" +
                            "   \"phone\": \"" + phone + "\",\n" +
                            "   \"rentTime\": " + rentTime + ",\n" +
                            "   \"deliveryDate\": \"" + deliveryDate + "\",\n" +
                            "   \"comment\": \"" + comment + "\",\n" +
                            "   \"color\": [\n" +
                            "        \"" + color + "\"\n" +
                            "    ]\n" +
                            "}")
                .when()
                .post("/api/v1/orders")
                .then();
    }
}