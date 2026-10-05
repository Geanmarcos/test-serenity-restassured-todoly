package starter.tasks;

import net.serenitybdd.screenplay.Actor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholders {

    private static final Pattern PATTERN = Pattern.compile("\\{(\\w+)}");

    private Placeholders() {}

    public static String resolve(Actor actor, String text) {
        if (text == null) return null;
        Matcher matcher = PATTERN.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            Object value = actor.recall(matcher.group(1));
            if (value == null) {
                throw new IllegalStateException(
                        "Variable no guardada: " + matcher.group(1));
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(value.toString()));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
