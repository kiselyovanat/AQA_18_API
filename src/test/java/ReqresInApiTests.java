import com.github.javafaker.Faker;
import models.*;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.GetResourceSpec.getResourceRequestSpec;
import static specs.GetResourceSpec.getResourceResponseSpec;
import static specs.GetSingleUserSpec.getSingleUserRequestSpec;
import static specs.GetSingleUserSpec.getSingleUserResponseSpec;
import static specs.GetUserNotFoundSpec.getUserNotFoundRequestSpec;
import static specs.GetUserNotFoundSpec.getUserNotFoundResponseSpec;
import static specs.PostCreateSpec.postCreateRequestSpec;
import static specs.PostCreateSpec.postCreateResponseSpec;
import static specs.PostLoginSuccessfulSpec.postLoginSuccessfulRequestSpec;
import static specs.PostLoginSuccessfulSpec.postLoginSuccessfulResponseSpec;

public class ReqresInApiTests {

    @Test
    void getSingleUserTest() {
        GetUserResponseModel response = step("Make request to get single user", () ->
                given(getSingleUserRequestSpec)
                        .when()
                        .get()
                        .then()
                        .spec(getSingleUserResponseSpec)
                        .extract().as(GetUserResponseModel.class));
        step("Verify last name in response", () ->
                assertThat(response.getData().getLast_name()).isEqualTo("Wong"));
    }

    @Test
    void getUserNotFindTest() {
        given(getUserNotFoundRequestSpec)
                .when()
                .get()
                .then()
                .spec(getUserNotFoundResponseSpec);
    }

    @Test
    void getResourceTest() {

        GetResourceResponseModel response = step("Make request to get resource", () ->
                given(getResourceRequestSpec)
                        .when()
                        .get()
                        .then()
                        .spec(getResourceResponseSpec)
                        .extract().as(GetResourceResponseModel.class));
        step("Verify name in response", () ->
                assertThat(response.getData().getName()).isEqualTo("mimosa"));
    }

    @Test
    void postCreateTest() {
        Faker faker = new Faker(new Locale("en"));
        String name = faker.name().firstName();
        String job = faker.job().title();

        CreateBodyModel createBody = new CreateBodyModel();
        createBody.setName(name);
        createBody.setJob(job);

        CreateResponseModel response = step("Make request to create a user", () ->
                given(postCreateRequestSpec)
                        .body(createBody)
                        .when()
                        .post()
                        .then()
                        .spec(postCreateResponseSpec)
                        .extract().as(CreateResponseModel.class));

        step("Verify name in response", () ->
                assertThat(response.getName()).isEqualTo(name));
        step("Verify job in response", () ->
                assertThat(response.getJob()).isEqualTo(job));

    }

    @Test
    void postLoginSuccessfulTest() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel response = step("Make request to login (successful)", () ->
                given(postLoginSuccessfulRequestSpec)
                .body(loginBody)
                .when()
                .post()
                .then()
                .spec(postLoginSuccessfulResponseSpec)
                .extract().as(LoginResponseModel.class));

        step("Verify token in response", () ->
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }
}
