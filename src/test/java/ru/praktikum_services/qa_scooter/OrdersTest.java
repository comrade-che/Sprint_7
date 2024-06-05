package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.emptyArray;

public class OrdersTest {

    private OrdersSteps ordersSteps = new OrdersSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("Check get list order. Check responce code successful. Check not empty array responce body")
    // •  тело ответа возвращается список заказов;
    public void shouldBeAuthCourierCheckResponse(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        ordersSteps
                .getArrayOrders()
                .body("orders", Matchers.not(emptyArray()))
                .statusCode(200);
    }

}
