package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Runs TC1–TC12 (single lab, cohort lab, batch provision) in PARALLEL — 2 threads.
 *
 * Run standalone:
 *   mvn test -Dtest=LabsProvisionRunner
 * Run via suite XML:
 *   mvn test -Dsurefire.suiteXmlFiles=testng-suites/labs-suite.xml
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        LabsProvisionRunner.ParallelConfig.class
})
@CucumberOptions(
        features   = { ".\\src\\test\\java\\FeatureFiles\\labs\\aws\\" },
        glue       = { "stepDefinitions" },
        dryRun     = false,
        plugin     = { "pretty" },
        monochrome = true,
        tags       = "@aws and not @lazy-lab-aws and not @aws-lab-provision-cleanup"
)
public class LabsProvisionRunner extends AbstractTestNGCucumberTests {

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
        String base = ExecutionContext.getReportFolder() + "/labs-provision";
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
