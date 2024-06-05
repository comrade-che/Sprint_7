package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

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
    // • курьера можно создать;
    // • запрос возвращает правильный код ответа;
    // • успешный запрос возвращает ok: true;
    public void createNewCourierCheckResponseAndValidBody(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        courierSteps
                .creatCourierValid()
                .body("ok", Matchers.is(true))
                .statusCode(201);
    }

    @Test
    @Step("Check not creat same courier. Check responce code broken")
    // • нельзя создать двух одинаковых курьеров;
    public void notCreateSameCourierCheckResponse(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierValid()
                .statusCode(409);
    }

    @Test
    @Step("Check not creat duplicate login courier. Check responce code broken. Check broken responce body")
    // • если создать пользователя с логином, который уже есть, возвращается ошибка
    public void notCreateDuplicateLoginCourierCheckResponseAndValidBody(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierValid()
                .body("message", Matchers.is(ResponseMessageLogin))
                .statusCode(409);
    }

    @Test
    @Step("Check not creat courier not obligatory fields. Check responce code broken. Check broken responce body")
    // • если одного из полей нет, запрос возвращает ошибку;
    public void notCreateCourierNotOneFieldsRequiredCheckResponseAndValidBody(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierInvalid()
                .body("message", Matchers.is(ResponseMessageField))
                .and()
                .statusCode(400);
    }

    @Test
    @Step("Check not creat courier with empty body. Check responce code broken")
    // • чтобы создать курьера, нужно передать в ручку все обязательные поля;
    public void notCreateCourierEmptyBodyCheckResponse(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        courierSteps
                .creatCourierValid();
        courierSteps.creatCourierEmpty()
                .statusCode(400);
    }

    @After
    public void tearDown() {
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        Integer id = courierSteps
                .loginCourier()
                .extract().body().path("id");
        if(id !=null) {
            courierSteps.delete(id);
        }
    }

}
