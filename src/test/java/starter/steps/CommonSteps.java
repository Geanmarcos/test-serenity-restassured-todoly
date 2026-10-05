package starter.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;
import starter.tasks.Placeholders;
import starter.tasks.SendRequest;

import static org.hamcrest.Matchers.equalTo;

public class CommonSteps {

    private EnvironmentVariables environmentVariables; // lo inyecta Serenity

    @Given("{actor} is an user on todo.ly")
    public void isAnUserOnTodoLy(Actor actor) {
        var config = EnvironmentSpecificConfiguration.from(environmentVariables);

        actor.whoCan(CallAnApi.at(config.getProperty("base.url")));
        actor.remember("user", config.getProperty("todoly.user"));
        actor.remember("password", config.getProperty("todoly.password"));
    }

    @When("{actor} sends a {word} request to {string}")
    public void sendsRequest(Actor actor, String method, String path) {
        actor.attemptsTo(SendRequest.of(method, path));
    }

    @When("{actor} sends a {word} request to {string} with body")
    public void sendsRequestWithBody(Actor actor, String method, String path, String body) {
        actor.attemptsTo(SendRequest.of(method, path, body));
    }

    @Then("the response code is {int}")
    public void responseCodeIs(int code) {
        SerenityRest.lastResponse().then().statusCode(code);
    }

    @And("the attribute {word} {string} is {string}")
    public void attributeIs(String type, String attribute, String expected) {
        Actor actor = OnStage.theActorInTheSpotlight();
        String value = Placeholders.resolve(actor, expected);

        switch (type.toLowerCase()) {
            case "int"     -> SerenityRest.lastResponse().then().body(attribute, equalTo(Integer.parseInt(value)));
            case "boolean" -> SerenityRest.lastResponse().then().body(attribute, equalTo(Boolean.parseBoolean(value)));
            default        -> SerenityRest.lastResponse().then().body(attribute, equalTo(value));
        }
    }

    @And("{actor} saves the value of {string} as {string}")
    public void savesValue(Actor actor, String jsonPath, String variableName) {
        String value = SerenityRest.lastResponse().jsonPath().getString(jsonPath);
        actor.remember(variableName, value);
    }

    @And("the response matches the schema {string}")
    public void matchesSchema(String schemaPath) {
        SerenityRest.lastResponse().then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }
}