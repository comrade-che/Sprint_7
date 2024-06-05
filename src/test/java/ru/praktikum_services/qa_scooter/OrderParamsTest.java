package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderParamsTest {

    private OrderParamsSteps orderSteps = new OrderParamsSteps();

    private List<String> color;

    @Parameterized.Parameters(name = "{index} color = {0}")
    public static Object[][] data() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of("")}
        };
    }

    public OrderParamsTest(List<String> color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("Check creat new orders by params color. Check responce code successful. Check not null value responce body")
    // • можно указать один из цветов — BLACK или GREY;
    // • можно указать оба цвета;
    // • можно совсем не указывать цвет;
    // • тело ответа содержит track.
    public void createNewOrderCheckResponseAndValidColor(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        orderSteps
                .creatOrderParamColor("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2024-06-06", "Saske, come back to Konoha", color)
                .statusCode(201)
                .body("track", notNullValue());
    }
}
