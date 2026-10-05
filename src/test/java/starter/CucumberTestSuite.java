package starter;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

//.\gradlew clean test --info  "-Dcucumber.filter.tags=@TestCrudClean" --console=plain
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "starter.steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel,"
                + "pretty,"
                + "html:build/reports/cucumber/cucumber.html,"
                + "json:build/reports/cucumber/cucumber.json,"
                + "timeline:build/test-results/timeline")
public class CucumberTestSuite {
}
