package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { ".\\src\\test\\java\\FeatureFiles\\" }, glue = {
		"stepDefinitions" }, dryRun = false, plugin = { "pretty", "testng:target/testng-report.xml",
				"html:target/cucumber.html" }, monochrome = true, tags = "@TC10"

)

public class TestngRunner extends AbstractTestNGCucumberTests {

}