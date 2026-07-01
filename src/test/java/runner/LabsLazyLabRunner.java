package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Runs TC13–TC16 (lazy lab) SEQUENTIALLY — 1 thread.
 *
 * Run standalone:
 *   mvn test -Dtest=LabsLazyLabRunner
 * Run via suite XML:
 *   mvn test -Dsurefire.suiteXmlFiles=testng-suites/labs-suite.xml
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        LabsLazyLabRunner.SequentialConfig.class
})
@CucumberOptions(
        features   = { ".\\src\\test\\java\\FeatureFiles\\labs\\aws\\lazy_lab_aws.feature" },
        glue       = { "stepDefinitions" },
        dryRun     = false,
        plugin     = { "pretty" },
        monochrome = true,
        tags       = "@lazy-lab-aws"
)
public class LabsLazyLabRunner extends AbstractTestNGCucumberTests {

    public static final class SequentialConfig implements ISuiteListener {
        @Override
        public void onStart(ISuite suite) {
            suite.getXmlSuite().setDataProviderThreadCount(1);
        }

        @Override
        public void onFinish(ISuite suite) {
        }
    }

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/labs-lazy";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:"   + base + "/cucumber.html,"  +
                "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }

    @Override
    @org.testng.annotations.DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
