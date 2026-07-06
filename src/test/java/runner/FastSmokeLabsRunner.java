package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Fast smoke run — AWS TC1, TC9 + GCP TC1, TC9 in parallel (2 threads).
 * Use this during active development for a ~25–30 min feedback cycle
 * instead of waiting for the full 2-hour combined suite.
 *
 * Run command:
 *   mvn test "-Dsurefire.suiteXmlFiles=testng-suites/fast-smoke-suite.xml"
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        FastSmokeLabsRunner.ParallelConfig.class
})
@CucumberOptions(
        features   = {
                ".\\src\\test\\java\\FeatureFiles\\labs\\aws\\",
                ".\\src\\test\\java\\FeatureFiles\\labs\\gcp\\"
        },
        glue       = { "stepDefinitions" },
        dryRun     = false,
        plugin     = { "pretty" },
        monochrome = true,
        tags       = "@smoke"
)
public class FastSmokeLabsRunner extends AbstractTestNGCucumberTests {

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
        String base = ExecutionContext.getReportFolder() + "/fast-smoke";
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
