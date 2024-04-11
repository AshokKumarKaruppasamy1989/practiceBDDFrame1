package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources//features//Login.feature",
glue = "stepDefinition",
monochrome = true,
plugin = {"pretty"})

public class LoginRunner extends AbstractTestNGCucumberTests {
	
	
}
