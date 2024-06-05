package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.nullValue;

public class LoginTest {

    private CourierSteps courierSteps = new CourierSteps();
    private LoginSteps loginSteps = new LoginSteps();

    String ResponseMessageEmpty = "Недостаточно данных для входа";
    String ResponseMessageBroken = "Учетная запись не найдена";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("Check courier successful auth. Check responce code successful. Check not null value responce body")
    // • курьер может авторизоваться;
    // • успешный запрос возвращает id;
    public void shouldBeAuthCourierCheckResponse(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        courierSteps
                .creatCourierValid();
        loginSteps
                .autorizeSuccessful()
                .body("id", Matchers.not(nullValue()))
                .statusCode(200);
    }

    @Test
    @Step("Check auth all obligatory fields. Check responce code broken. Check responce body broken")
    // • для авторизации нужно передать все обязательные поля;
    // • если какого-то поля нет, запрос возвращает ошибку;
    public void shouldBeAllFieldNotEmptyCheckResponseAndValidBody(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        loginSteps
                .autorizeEmptyFields()
                .body("message", Matchers.is(ResponseMessageEmpty))
                .statusCode(400);
    }

    @Test
    @Step("Check auth incorrect login or password. Check responce code broken. Check responce body broken")
    // • система вернёт ошибку, если неправильно указать логин или пароль;
    // • если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    public void shouldBeValidLoginAndPasswordCheckResponseAndValidBody(){
        //RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        loginSteps
                .autorizeBroken()
                .body("message", Matchers.is(ResponseMessageBroken))
                .statusCode(404);
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
