package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(features = { ".\\src\\test\\java\\FeatureFiles\\labs\\lazy_lab.feature", }, glue = {
        "stepDefinitions" }, dryRun = false, plugin = { "pretty" }, monochrome = true, tags = ""
// No default tag — run all scenarios.
// Override at runtime: mvn test -Dcucumber.filter.tags="@smoke"
)
public class TestngRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder();
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:" + base + "/cucumber.html," +
                        "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }
}