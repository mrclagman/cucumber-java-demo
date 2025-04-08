package cucumberjavademo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddCourseStepDefinitions {

    private World world;
    private String url = "http://localhost:8080/api/courses";
    private String successfulAddCourseMsg = "Course Added successfully!";

    Map<String , Object> requestBody = new HashMap<>();

    public AddCourseStepDefinitions(World world) {
        this.world = world;
    }

    @Given("an admin creates a random course")
    public void an_admin_creates_a_random_course() {

        requestBody.put("title", "Free-Throws-101");
        requestBody.put("description", "How to be an effective free throw shooter");
        requestBody.put("full", true);

        System.out.printf("Request body: %s\n", requestBody);

    }
    @When("admin adds the course")
    public void admin_adds_the_course() {

        world.request = given()
                .header("X-API-KEY", "Test1234")
                .contentType(ContentType.JSON)
                .body(requestBody);

        world.response = world.request
                .when()
                .post(url);
    }
    @Then("response body should contain correct schema for addition of course")
    public void response_body_should_contain_correct_schema_for_addition_of_course() {
        world.response
                .then()
                .assertThat()
                .body(
                JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schema/addCourse.json"))
        );
    }
    @Then("response body should contain message for successful course addition")
    public void response_body_should_contain_message_for_successful_course_addition() {

        world.response
                .then()
                .assertThat()
                .body("message", equalTo(successfulAddCourseMsg));
    }
}
