package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Runs TC1–TC12 (single lab, cohort lab, batch provision) in PARALLEL.
 *
 * Run standalone:
 *   mvn test -Dtest=LabsProvisionRunner
 * Run via suite XML:
 *   mvn test -Dsurefire.suiteXmlFiles=testng-suites/labs-suite.xml
 * Override thread count:
 *   mvn test -Dtest=LabsProvisionRunner -Dthread.count=4
 * Override tag scope (e.g. to also include TC13-16 lazy lab in one run):
 *   mvn test -Dtest=LabsProvisionRunner -Dcucumber.filter.tags="@AWS_TC1 or @AWS_TC2 ... or @AWS_TC16"
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
        // Excludes lazy-lab, cleanup, AND lab-actions (TC17+) — all three share the
        // feature-level @aws tag with TC1-12, so each must be excluded explicitly
        // or they silently run alongside TC1-12 here.
        tags       = "@aws and not @lazy-lab-aws and not @aws-lab-provision-cleanup and not @lab-actions-aws"
)
public class LabsProvisionRunner extends AbstractTestNGCucumberTests {

    public static final class ParallelConfig implements ISuiteListener {
        @Override
        public void onStart(ISuite suite) {
            suite.getXmlSuite().setDataProviderThreadCount(Integer.getInteger("thread.count", 2));
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
                "testng:" + base + "/testng-report.xml" + "," +
                "rerun:" + base + "/failed_scenarios.txt");
        super.setUpClass();
    }

    @Override
    @org.testng.annotations.DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
