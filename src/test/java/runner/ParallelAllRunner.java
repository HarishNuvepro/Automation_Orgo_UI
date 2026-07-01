package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = {
        ".\\src\\test\\java\\FeatureFiles\\login\\",
        ".\\src\\test\\java\\FeatureFiles\\users\\",
        ".\\src\\test\\java\\FeatureFiles\\labs\\"
    },
    glue       = { "stepDefinitions" },
    dryRun     = false,
    plugin     = { "pretty" },
    monochrome = true,
    tags       = "not @AWS_TC58 and not @AWS_TC59 and not @GCP_TC58 and not @GCP_TC59"
)
public class ParallelAllRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/parallel";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:"   + base + "/cucumber.html," +
                "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
