package RestApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DummyJsonQuotes {
    
    private String quoteSchema;
    private String quotesSchema;

    @BeforeClass
    public void setup() throws IOException {
        RestAssured.baseURI = "https://dummyjson.com";
        loadProperties();
    }
    
    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("AdvantageProperties.properties")) {
            properties.load(input);
            quoteSchema = properties.getProperty("quote.schema");
            quotesSchema = properties.getProperty("quotes.schema");
        }
    }
    
    @Test
    public void testGetQuotes() {
        given()
            .when()
                .get("/quotes")
            .then()
                .statusCode(200)
                .body("quotes.size()", greaterThan(0))
                .body("total", equalTo(1454))
                .body("quotes[0].id", equalTo(1))
                .body("quotes[0].quote", not(emptyString()))
                .body("quotes[0].author", not(emptyString()));
    }
    
    @Test
    public void testGetSpecificQuote() {
        given()
            .when()
                .get("/quotes/1")
            .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("quote", not(emptyString()))
                .body("author", not(emptyString()));
    }
    
    @Test
    public void testQuoteSchemaValidation() {
        given()
            .when()
                .get("/quotes")
            .then()
                .statusCode(200)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(quotesSchema));
    }

    @Test
    public void testGetSingleQuote() {
        given()
            .when()
                .get("/quotes/1")
            .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("quote", not(emptyString()))
                .body("author", not(emptyString()));
    }

    @Test
    public void testSingleQuoteSchemaValidation() {
        given()
            .when()
                .get("/quotes/1")
            .then()
                .statusCode(200)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(quoteSchema));
    }
    
    @Test
    public void testGetRandomQuote() {
        given()
            .when()
                .get("/quotes/random")
            .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("quote", not(emptyString()))
                .body("author", not(emptyString()));
    }

    @Test
    public void testGetRandomQuoteWithLength() {
        given()
            .when()
                .get("/quotes/random/10")
            .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("quote", not(emptyString()))
                .body("author", not(emptyString()));
    }

    @Test
    public void testLimitAndSkipQuotes() {
        given()
            .queryParam("limit", 3)
            .queryParam("skip", 10)
            .log().all()  // Log the request details
            .when()
                .get("/quotes")
            .then()
                .log().all()  // Log the response details
                .statusCode(200)
                .body("quotes.size()", equalTo(3)) // Check if 3 quotes are returned
                .body("total", equalTo(1454))       // Check total number of quotes
                .body("skip", equalTo(10));         // Check that 10 items were skipped
    }

    @Test
    public void testLimitZeroQuotes() {
        given()
            .queryParam("limit", 5) // Fetch all quotes
            .log().all()  // Log the request details
            .when()
                .get("/quotes")
            .then()
                .log().all()  // Log the response details
                .statusCode(200)
                .body("quotes.size()", greaterThan(0)); // Check that at least some quotes are returned
    }
}
