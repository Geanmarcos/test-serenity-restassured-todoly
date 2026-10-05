package starter.steps;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.model.util.EnvironmentVariables;
import starter.models.request.ProjectRequest;

import static org.hamcrest.Matchers.equalTo;

public class crudSteps {

    private EnvironmentVariables environmentVariables;
    private String baseUrl;

    //{actor} es propio de serenity
    @Given("{actor} is an user on todo.ly v1")
    public void carlosIsAnUserOnTodoLyV1(Actor actor) {
        baseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getProperty("base.url");
        actor.whoCan(CallAnApi.at(baseUrl));
    }

    @When("{actor} create a project with Content {string} & Icon {string}")
    public void heCreateAProjectWith(Actor actor, String projectName, String icon) {

        String user = EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getProperty("todoly.user");

        String password = EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getProperty("todoly.password");

        actor.attemptsTo(
                Post.to("/api/projects.json")
                        .with(spec -> spec
                                .auth().preemptive().basic(user, password)
                                .contentType("application/json")
                                .body(ProjectRequest.builder()
                                        .content(projectName)
                                        .icon(Integer.parseInt(icon))
                                        .build()))
        );
    }

    @Then("{actor} should see the project {string}")
    public void heShouldSeeTheProject(Actor actor, String projectName) {
        SerenityRest.lastResponse()
                .then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo(projectName));
    }
}
