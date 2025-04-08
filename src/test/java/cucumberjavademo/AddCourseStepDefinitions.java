package cucumberjavademo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddCourseStepDefinitions {

    private World world;
    private String url = "http://localhost:8080/api/courses";
    private Response response;
    private String responseBody;
    private RequestSpecification request;
    private String successfulAddCourseMsg = "Course Added successfully!";

    Map<String , Object> requestBody = new HashMap<>();

    public AddCourseStepDefinitions(World world) {
        this.world = world;
    }

    @Given("an admin creates a random course")
    public void an_admin_creates_a_random_course() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//        request = given();
//        response = request.when().;

        this.requestBody.put("title", "Free-Throws-101");
        this.requestBody.put("description", "How to be an effective free throw shooter");
        this.requestBody.put("full", true);

        System.out.printf("Request body: %s\n", this.requestBody);

    }
    @When("admin adds the course")
    public void admin_adds_the_course() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();

        this.world.request = given().contentType(ContentType.JSON).body(this.requestBody);
        this.world.response = this.world.request.when().post(url);
    }
    @Then("response body should contain correct schema for addition of course")
    public void response_body_should_contain_correct_schema_for_addition_of_course() {
        this.world.response.then().assertThat().body(
                JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schema/addCourse.json"))
        );
    }
    @Then("response body should contain message for successful course addition")
    public void response_body_should_contain_message_for_successful_course_addition() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        this.world.response.then()
                .assertThat()
                .body("message", equalTo(successfulAddCourseMsg));
    }
}
