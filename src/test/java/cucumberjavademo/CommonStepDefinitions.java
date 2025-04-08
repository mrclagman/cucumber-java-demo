package cucumberjavademo;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class CommonStepDefinitions {
    private World world;

    public CommonStepDefinitions(World world) {
        this.world = world;
    }

    @Then("response should be status {int}")
    public void response_should_be_status(Integer statusCode) {

        this.world.response.then().statusCode(statusCode);


    }
}
