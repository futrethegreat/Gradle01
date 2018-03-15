package Runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author DavidSauce
 *
 */

@RunWith(Cucumber.class) // Se utiliza en JUnit
@CucumberOptions(features = { "src/test/java/Features" }, glue = { "Steps" }, plugin = {
    "json:target/cucumber.json", "html:target/site/cucumber-pretty" })

public class TestRunner { // Se utiliza en Junit

}
