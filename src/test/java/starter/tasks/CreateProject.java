package starter.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import starter.models.request.ProjectRequest;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateProject implements Task {

    private final ProjectRequest request;

    public CreateProject(ProjectRequest request) {
        this.request = request;
    }

    public static CreateProject with(ProjectRequest request) {
        return instrumented(CreateProject.class, request);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(SendRequest.of("POST", "/api/projects.json", request));
    }
}