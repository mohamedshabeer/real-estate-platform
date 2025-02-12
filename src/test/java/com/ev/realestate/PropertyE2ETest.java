package com.ev.realestate;

import com.ev.realestate.request.PropertyRequest;
import com.ev.realestate.response.PropertyResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PropertyE2ETest {

    private static final String API_KEY = "TEST-API-KEY";
    private static final String API_KEY_HEADER = "X-API-Key";
    private static final String BASE_URI = "http://localhost:8080/api";
    private static final String PROPERTIES_ENDPOINT = "/properties";

    @BeforeEach
    void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void testPropertyRequest() {
        // Create property
        PropertyRequest propertyRequest = new PropertyRequest(
                "Luxury Villa", "Stunning 5-bedroom villa with pool",
                "Hamburg", 5_500_000.0
        );

        PropertyResponse createdProperty = given()
                .header(API_KEY_HEADER, API_KEY)
                .contentType(ContentType.JSON)
                .body(propertyRequest)
                .when()
                .post(PROPERTIES_ENDPOINT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("title", equalTo(propertyRequest.title()))
                .extract().as(PropertyResponse.class);

        // Verify retrieval
        given()
                .header(API_KEY_HEADER, API_KEY)
                .when()
                .get(PROPERTIES_ENDPOINT + "/{id}", createdProperty.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("price", equalTo(5_500_000.0f));

        // Verify delete
        given()
                .header(API_KEY_HEADER, API_KEY)
                .when()
                .delete(PROPERTIES_ENDPOINT + "/{id}", createdProperty.id())
                .then()
                .statusCode(HttpStatus.OK.value());

        // Verify not found after deletion
        given()
                .header(API_KEY_HEADER, API_KEY)
                .when()
                .get(PROPERTIES_ENDPOINT + "/{id}", createdProperty.id())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldReturnUnauthorizedWithoutApiKey() {
        given()
                .contentType(ContentType.JSON)
                .body(new PropertyRequest("Test", "Desc", "Loc", 100000.0))
                .when()
                .post(PROPERTIES_ENDPOINT)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
