package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
    features   = {
            ".\\src\\test\\java\\FeatureFiles\\labs\\aws\\lab_actions_aws.feature",
            ".\\src\\test\\java\\FeatureFiles\\labs\\gcp\\lab_actions_gcp.feature"
    },
    glue       = { "stepDefinitions" },
    dryRun     = false,
    plugin     = { "pretty" },
    monochrome = true,
    tags       = "@AWS_TC50 or @GCP_TC50"
)
public class CleanupRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/cleanup";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:"   + base + "/cucumber.html," +
                "testng:" + base + "/testng-report.xml" + "," +
                "rerun:" + base + "/failed_scenarios.txt");
        super.setUpClass();
    }
    // No @DataProvider override — runs sequentially after all parallel scenarios complete
}
