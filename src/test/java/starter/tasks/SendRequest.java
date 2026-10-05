package starter.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.serenitybdd.screenplay.rest.questions.RestQueryFunction;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SendRequest implements Task {

    private final String method;
    private final String path;
    private final Object body; // String JSON o POJO

    public SendRequest(String method, String path, Object body) {
        this.method = method;
        this.path = path;
        this.body = body;
    }

    public static SendRequest of(String method, String path) {
        return instrumented(SendRequest.class, method, path, null);
    }

    public static SendRequest of(String method, String path, Object body) {
        return instrumented(SendRequest.class, method, path, body);
    }

    @Override
    @Step("{0} sends a #method request to #path")
    public <T extends Actor> void performAs(T actor) {
        String resolvedPath = Placeholders.resolve(actor, path);

        RestQueryFunction spec = request -> {
            String user = actor.recall("user");
            String password = actor.recall("password");

            if (user != null && password != null) {
                request.auth().preemptive().basic(user, password);
            }

            request.contentType("application/json");

            if (body instanceof String json) {
                if (!json.isBlank()) {
                    request.body(Placeholders.resolve(actor, json));
                }
            } else if (body != null) {
                request.body(body);
            }

            return request;
        };

        Performable interaction = switch (method.toUpperCase()) {
            case "GET"    -> Get.resource(resolvedPath).with(spec);
            case "POST"   -> Post.to(resolvedPath).with(spec);
            case "PUT"    -> Put.to(resolvedPath).with(spec);
            case "DELETE" -> Delete.from(resolvedPath).with(spec);
            default -> throw new IllegalArgumentException(
                    "Método HTTP no soportado: " + method);
        };

        actor.attemptsTo(interaction);
    }
}