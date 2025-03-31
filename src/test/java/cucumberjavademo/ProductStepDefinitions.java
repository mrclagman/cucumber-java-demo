package cucumberjavademo;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class ProductStepDefinitions {

    private Response response;
    private String responseBody;


    private RequestSpecification request;
    private String server = "https://fakestoreapi.com";
    private int productId;
    private String jsonData;

    private String updatedField;
    private String newValue;


    @Given("an admin wants to update the {string} of an existing product with id {int}")
    public void an_admin_wants_to_update_the_of_an_existing_product(String field, int id) throws JsonProcessingException {
        this.productId = id;
        String url = this.server + "/products/" + id;
        request = given();
        response = request.when().get(url);

        response.then().statusCode(200);
        responseBody = response.getBody().asString();

        System.out.println("\nJSON Response: ");
        System.out.println(responseBody);

        Map result = new ObjectMapper().readValue(responseBody, Map.class);

        System.out.println("\nMap:");
        System.out.println(result);



        String newValue = "This is a new VALUE!";
        result.put(field, newValue);

        this.updatedField = field;
        this.newValue = newValue;


        System.out.println("\nMap:");
        System.out.println(result);

        ObjectMapper mapper = new ObjectMapper();
        this.jsonData = mapper.writeValueAsString(result);
    }
    @When("an admin updates the product")
    public void an_admin_updates_the_product() {

        String url = this.server + "/products/" + this.productId;

        System.out.println("When(): jsonData");
        System.out.printf(this.jsonData);

        request = given().contentType("application/json").body(this.jsonData);
        response = request.when().put(url);


    }
    @Then("response should be status {int}")
    public void response_should_be_status(Integer statusCode) {


       response.then().statusCode(statusCode);


    }
    @Then("response body should contain the updated {string}")
    public void response_body_should_contain_the_updated(String field) {
        responseBody = response.getBody().asString();

        System.out.println("Then()\nJSON Response: ");
        System.out.println(responseBody);

        response.then().body(field, equalTo(newValue));

    }
}
