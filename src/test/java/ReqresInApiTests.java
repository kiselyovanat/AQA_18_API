import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import com.github.javafaker.Faker;

import java.util.Locale;

public class ReqresInApiTests {

    public static final String BASE_URL = "https://reqres.in/api";

    @Test
    void getSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get(BASE_URL + "/users/3")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.last_name", is("Wong"));
    }

    @Test
    void getUserNotFindTest() {
        given()
                .log().uri()
                .when()
                .get(BASE_URL + "/users/100")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void getResourceTest() {
        given()
                .log().uri()
                .when()
                .get(BASE_URL + "/unknown/10")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.name", is("mimosa"));
    }

    @Test
    void postCreateTest() {
        Faker faker = new Faker(new Locale("en"));
        String name = faker.name().firstName();
        String job = faker.job().title();
        String body = "{ \"name\": \"" + name +"\", \"job\": \"" + job + "\" }";
        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post(BASE_URL + "/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(name))
                .body("job", is(job));
    }

    @Test
    void postLoginSuccessfulTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post(BASE_URL + "/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
