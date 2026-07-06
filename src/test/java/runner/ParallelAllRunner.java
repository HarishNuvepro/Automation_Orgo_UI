package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

/**
 * Override thread count:
 *   mvn test -Dtest=ParallelAllRunner -Dthread.count=6 -Dcucumber.filter.tags="..."
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        ParallelAllRunner.ParallelConfig.class
})
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
    tags       = "not @AWS_TC44 and not @AWS_TC50 and not @GCP_TC44 and not @GCP_TC50"
)
public class ParallelAllRunner extends AbstractTestNGCucumberTests {

    public static final class ParallelConfig implements ISuiteListener {
        @Override
        public void onStart(ISuite suite) {
            suite.getXmlSuite().setDataProviderThreadCount(Integer.getInteger("thread.count", 4));
        }

        @Override
        public void onFinish(ISuite suite) {
        }
    }

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/parallel";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:"   + base + "/cucumber.html," +
                "testng:" + base + "/testng-report.xml" + "," +
                "rerun:" + base + "/failed_scenarios.txt");
        super.setUpClass();
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
