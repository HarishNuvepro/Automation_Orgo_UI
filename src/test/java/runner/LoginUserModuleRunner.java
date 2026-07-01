package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Combined runner — Login module (parallel) + User module (sequential).
 *
 * Run options:
 *   mvn test -Dtest=LoginUserModuleRunner          (via class filter)
 *   mvn test -PloginUsers                          (via Maven profile)
 *
 * Execution mode is controlled here:
 *   - Change THREAD_COUNT to 1  → fully sequential  (safe for users)
 *   - Change THREAD_COUNT to 3+ → parallel          (faster for login, may be flaky for users)
 *
 * To run each module separately with its own tuned settings:
 *   mvn test -Dtest=LoginModuleRunner              (login — parallel)
 *   mvn test -Dtest=UserModuleRunner               (users  — sequential)
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        LoginUserModuleRunner.ExecutionConfig.class
})
@CucumberOptions(
        features   = {
                ".\\src\\test\\java\\FeatureFiles\\login\\",
                ".\\src\\test\\java\\FeatureFiles\\users\\"
        },
        glue       = { "stepDefinitions" },
        dryRun     = false,
        plugin     = { "pretty" },
        monochrome = true,
        tags       = "@login or @users"
)
public class LoginUserModuleRunner extends AbstractTestNGCucumberTests {

    /**
     * Set THREAD_COUNT = 1 for sequential (users-safe).
     * Set THREAD_COUNT = 3 for parallel (login-optimised).
     */
    private static final int THREAD_COUNT = 1;

    public static final class ExecutionConfig implements ISuiteListener {
        @Override
        public void onStart(ISuite suite) {
            suite.getXmlSuite().setDataProviderThreadCount(THREAD_COUNT);
        }

        @Override
        public void onFinish(ISuite suite) {
        }
    }

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/login-users";
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
