import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInApiTests {

    @Test
    void getSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/3")
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
                .get("https://reqres.in/api/users/100")
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
                .get("https://reqres.in/api/unknown/10")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.name", is("mimosa"));
    }

    @Test
    void postCreateTest() {
        String body = "{ \"name\": \"Natasha\", \"job\": \"QA\" }";
        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Natasha"))
                .body("job", is("QA"));
    }

    @Test
    void postLoginSuccessfulTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}