import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class mainTest {

    @BeforeAll
    public void setup(){
        RestAssured.
                filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = "https://deckofcardsapi.com/";
    }

    @Test
    @DisplayName("verify response status")
    public void testStatusCode() {

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("api/deck/new/draw/?count={id}", 2);

        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        assertEquals(statusCode, 200, "Correct status code returned");

    }

    @Test
    @DisplayName("verify header value")
    public void testGetHeader() {

        when().
                get("api/deck/new/draw/?count={id}", 2).
                then().
                statusCode(200).
                statusLine("HTTP/1.1 200 OK").
                assertThat().header("Content-Type", "application/json").
                header("Content-Type", "application/json");

    }

    @Test
    @DisplayName("verify time execution")
    public void testGetTime() {

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("api/deck/new/draw/?count={id}", 2);

        int timeExecution = Math.toIntExact(response.getTime());

        System.out.println("===== Status and Time Execution =====");

        System.out.println("Total time execution : " + timeExecution);

        if (timeExecution >= 2000) {
            System.out.println("Time execution status is Bad with " + timeExecution);
        } else {
            System.out.println("Time execution status is Good with " + timeExecution);
        }

    }

    @Test
    @DisplayName("verify total data")
    public void testGetTotalData() {

        when().
                get("api/deck/new/draw/?count={id}", 2).
                then().
                statusCode(200).
                statusLine("HTTP/1.1 200 OK").
                assertThat().body("size()", is(4)).
                header("Content-Type", "application/json");

    }

    @Test
    @DisplayName("verify specific deck_id")
    public void testGetDeckID() {

        // use deck_id: bv43wewgkl6s
        when().
                get("api/deck/bv43wewgkl6s/draw/?count={id}", 2).
                then().
                statusCode(200).
                statusLine("HTTP/1.1 200 OK").
                assertThat().body("deck_id", is("bv43wewgkl6s")).
                header("Content-Type", "application/json");

    }

    @Test
    @DisplayName("verify total cards")
    public void testGetTotalCards() {

        when().
                get("api/deck/bv43wewgkl6s/draw/?count={id}", 2).
                then().
                statusCode(200).
                statusLine("HTTP/1.1 200 OK").
                assertThat().body("cards.size()", is(2)).
                header("Content-Type", "application/json");

    }

    @Test
    @DisplayName("verify remaining")
    public void testGetRemaining() {

        // Declare remaining code
        int remainingCode = 50;

        when().
                get("api/deck/new/draw/?count={id}", 2).
                then().
                statusCode(200).
                statusLine("HTTP/1.1 200 OK").
                assertThat().body("remaining", is(remainingCode)).
                header("Content-Type", "application/json");

    }

}
