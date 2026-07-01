package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Runs GCP_TC1–GCP_TC12 (single lab, cohort lab, batch provision) in PARALLEL — 2 threads.
 *
 * Run standalone:
 *   mvn test -Dtest=GcpLabsRunner
 * Run via suite XML:
 *   mvn test -Dsurefire.suiteXmlFiles=testng-suites/gcp-labs-suite.xml
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        GcpLabsRunner.ParallelConfig.class
})
@CucumberOptions(
        features   = { ".\\src\\test\\java\\FeatureFiles\\labs\\gcp\\" },
        glue       = { "stepDefinitions" },
        dryRun     = false,
        plugin     = { "pretty" },
        monochrome = true,
        tags       = "@gcp and not @lazy-lab-gcp"
)
public class GcpLabsRunner extends AbstractTestNGCucumberTests {

    public static final class ParallelConfig implements ISuiteListener {
        @Override
        public void onStart(ISuite suite) {
            suite.getXmlSuite().setDataProviderThreadCount(2);
        }

        @Override
        public void onFinish(ISuite suite) {
        }
    }

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/gcp-labs";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:"   + base + "/cucumber.html,"  +
                "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }

    @Override
    @org.testng.annotations.DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
