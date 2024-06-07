package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierTest {

    String ResponseMessageLogin = "Этот логин уже используется. Попробуйте другой.";
    String ResponseMessageField = "Недостаточно данных для создания учетной записи";

    private CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("Check creat new courier. Check responce code successful. Check successful responce body")
    public void createNewCourierCheckResponseAndValidBody(){
        courierSteps
                .creatCourierValid()
                .body("ok", Matchers.is(true))
                .statusCode(201);
    }

    @Test
    @Step("Check not creat same courier. Check responce code broken")
    public void notCreateSameCourierCheckResponse(){
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierValid()
                .statusCode(409);
    }

    @Test
    @Step("Check not creat duplicate login courier. Check responce code broken. Check broken responce body")
    public void notCreateDuplicateLoginCourierCheckResponseAndValidBody(){
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierValid()
                .body("message", Matchers.is(ResponseMessageLogin))
                .statusCode(409);
    }

    @Test
    @Step("Check not creat courier not obligatory fields. Check responce code broken. Check broken responce body")
    public void notCreateCourierNotOneFieldsRequiredCheckResponseAndValidBody(){
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierInvalid()
                .body("message", Matchers.is(ResponseMessageField))
                .and()
                .statusCode(400);
    }

    @Test
    @Step("Check not creat courier with empty body. Check responce code broken")
    public void notCreateCourierEmptyBodyCheckResponse(){
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierEmpty()
                .statusCode(400);
    }

    @After
    public void tearDown() {
        Integer id = courierSteps
                .loginCourier()
                .extract().body().path("id");
        if(id !=null) {
            courierSteps.delete(id);
        }
    }

}
