package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Runs user_management.feature scenarios (TC3-TC20 + TC_CLEANUP) in SEQUENTIAL order.
 *
 * Run options:
 *   mvn test -Dtest=UserActionsRunner         (standalone — actions + cleanup only)
 *   mvn test -Dsurefire.suiteXmlFiles=testng-suites/users-suite.xml
 *                                             (create parallel → actions sequential)
 *
 * TC_CLEANUP at the end removes all users registered during the run.
 * When run via the suite XML, this includes users created by UserCreateRunner too.
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        UserActionsRunner.SequentialConfig.class
})
@CucumberOptions(
        features   = { ".\\src\\test\\java\\FeatureFiles\\users\\user_management.feature" },
        glue       = { "stepDefinitions" },
        dryRun     = false,
        plugin     = { "pretty" },
        monochrome = true,
        tags       = "@useractions or @usercleanup"
)
public class UserActionsRunner extends AbstractTestNGCucumberTests {

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
        String base = ExecutionContext.getReportFolder() + "/user-actions";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:"   + base + "/cucumber.html,"  +
                "testng:" + base + "/testng-report.xml" + "," +
                "rerun:" + base + "/failed_scenarios.txt");
        super.setUpClass();
    }

    @Override
    @org.testng.annotations.DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
