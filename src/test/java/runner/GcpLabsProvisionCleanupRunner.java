package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
    features   = { ".\\src\\test\\java\\FeatureFiles\\labs\\gcp\\gcp_lab_provision_cleanup.feature" },
    glue       = { "stepDefinitions" },
    dryRun     = false,
    plugin     = { "pretty" },
    monochrome = true,
    tags       = "@TC_CLEANUP_GCP_LABS"
)
public class GcpLabsProvisionCleanupRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/gcp-labs-provision-cleanup";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:"   + base + "/cucumber.html," +
                "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }
    // No @DataProvider(parallel=true) override — runs sequentially after all parallel scenarios
}
