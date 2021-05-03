package br.com.kauebarreto.jsondiff;

import br.com.kauebarreto.jsondiff.model.Diff;
import br.com.kauebarreto.jsondiff.repository.DiffRepository;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonDiffIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @LocalServerPort
    private int port;

    @Autowired
    private DiffRepository diffRepository;

    @BeforeEach
    public void init(){
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        RestAssured.port = port;
        diffRepository.deleteAll();

    }

    public String encode64(String value){
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void shouldReturn404WhenISearchForUnsavedDiff(){
        given()
        .when()
                .get("/v1/diff/1")
        .then()
                .statusCode(404);
    }

    public void saveLeftAndRight(String left, String right){
        given()
            .body("{\"data\": \"" + encode64(left) + "\"}")
            .contentType("application/json")
        .when()
            .put("/v1/diff/1/left")
        .then()
                .statusCode(200);
        given()
            .body("{\"data\": \"" + encode64(right) + "\"}")
            .contentType("application/json")
        .when()
            .put("/v1/diff/1/right")
        .then()
            .statusCode(200);
    }

    @Test
    public void shouldReturn200WhenDiffAreEquals(){
        saveLeftAndRight("Hello World", "Hello World");
        given()
        .when()
            .get("/v1/diff/1")
        .then()
            .statusCode(200)
            .body("result", equalTo("The both content are identical"));
        ;
    }

    @Test
    public void shouldReturn406WhenDiffHasDifferentSize(){
        saveLeftAndRight("Hello World", "Hello New World");
        given()
        .when()
            .get("/v1/diff/1")
        .then()
            .statusCode(406)
            .body("result", equalTo("Different size contents expected [11] but found [15]"));
        ;
    }

    @Test
    public void shouldReturn406WhenDiffHasSameSizeButDifferentContent(){
        saveLeftAndRight("Hello World", "Hello world");
        given()
        .when()
            .get("/v1/diff/1")
        .then()
            .statusCode(406)
            .body("result", equalTo("Offset: [6] Length: [11]"));
        ;
    }
}
