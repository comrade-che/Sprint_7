package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import org.hamcrest.Matchers;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
    public void shouldBeAuthCourierCheckResponse(){
        courierSteps
                .creatCourierValid();
        loginSteps
                .autorizeSuccessful()
                .body("id", Matchers.not(nullValue()))
                .statusCode(200);
    }

    @Test
    @Step("Check auth all obligatory fields. Check responce code broken. Check responce body broken")
    public void shouldBeAllFieldNotEmptyCheckResponseAndValidBody(){
        loginSteps
                .autorizeEmptyFields()
                .body("message", Matchers.is(ResponseMessageEmpty))
                .statusCode(400);
    }

    @Test
    @Step("Check auth incorrect login or password. Check responce code broken. Check responce body broken")
    public void shouldBeValidLoginAndPasswordCheckResponseAndValidBody(){
        loginSteps
                .autorizeBroken()
                .body("message", Matchers.is(ResponseMessageBroken))
                .statusCode(404);
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
