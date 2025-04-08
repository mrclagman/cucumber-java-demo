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

public class UpdateCourseStepDefinitions {
    private World world;
    private String url = "http://localhost:8080/api/courses";
    private String successfulUpdateCourseMsg = "Course updated successfully!";
    private int courseId;

    Map<String , Object> originalRequest = new HashMap<>();
    Map<String , Object> updateRequest = new HashMap<>();


    public UpdateCourseStepDefinitions(World world) {
        this.world = world;
    }

    @Given("an admin has created a course")
    public void an_admin_has_created_a_course() {
        originalRequest.put("title", "This course should be full");
        originalRequest.put("description", "This description should be updated later");
        originalRequest.put("full", false);

        System.out.printf("Request body: %s\n", this.originalRequest);

        world.request = given()
                .header("X-API-KEY", "Test1234")
                .contentType(ContentType.JSON)
                .body(originalRequest);

        world.response = world.request.when().post(url);

        world.response.then().statusCode(201);
        courseId = world.response.then().extract().path("id");

        System.out.printf("id: %s\n", courseId);

    }

    @Given("admin wants to update the course details")
    public void admin_wants_to_update_the_course_details() {
        updateRequest.put("title", "This is an updated course title");
        updateRequest.put("description", "This description was already updated and set to full");
        updateRequest.put("full", true);

        System.out.printf("Request body: %s\n", updateRequest);

    }

    @When("admin updates the course")
    public void admin_updates_the_course() {
        String updateUrl = this.url + "/" + courseId;

        world.request = given()
                .header("X-API-KEY", "Test1234")
                .contentType(ContentType.JSON)
                .body(this.updateRequest);

        world.response = this.world.request.when().put(updateUrl);

        System.out.println(world.response.then().extract().response().asString());
    }

    @Then("response body should contain correct schema for update of course")
    public void response_body_should_contain_correct_schema_for_update_of_course() {
        world.response.then().assertThat().body(
                JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schema/updateCourse.json"))
        );
    }

    @Then("response body should contain message for successful course update")
    public void response_body_should_contain_message_for_successful_course_update() {
        world.response.then()
                .assertThat()
                .body("message", equalTo(successfulUpdateCourseMsg));
    }
}
