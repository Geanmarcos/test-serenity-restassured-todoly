package starter.basic;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.response.Response;

@ExtendWith(SerenityJUnit5Extension.class)
public class BasicSerenity {

    /*
    @Test
    void createProject() {

        Actor actor = Actor
                .named("Juan Perez")
                .whoCan(CallAnApi.at("https://todo.ly"));

        actor.attemptsTo(
                Post
                        .to("/api/projects.json")
                        .with(requestSpecification ->
                                requestSpecification
                                        .given()
                                        .auth().preemptive().basic("geanmarcos.tataje@gmail.com", "TestingJB$.")
                                        .body("""
                                    {
                                        "Content": "Serenity ScreenPlay",
                                        "Icon": 5
                                    }
                                    """)
                                        .log().all()
                        )
        );

        SerenityRest.lastResponse().then().log().all();
    }
*/

}
