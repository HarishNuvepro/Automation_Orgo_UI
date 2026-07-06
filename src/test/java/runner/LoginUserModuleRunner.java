package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Combined runner — Login module + User module, run together.
 *
 * Run options:
 *   mvn test -Dtest=LoginUserModuleRunner          (via class filter)
 *   mvn test -PloginUsers                          (via Maven profile)
 *
 * Execution mode is controlled via system properties (both optional, with safe defaults):
 *   -Dthread.count=N     → parallel scenario thread count (default 1 = sequential)
 *   -Dtest.headless=true → headless browser (default true, per Hook.java)
 *
 * Example — 3 threads, headless:
 *   mvn test -Dtest=LoginUserModuleRunner -Dthread.count=3 -Dtest.headless=true
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

    /** Override at runtime with -Dthread.count=N. Defaults to 1 (sequential). */
    private static final int THREAD_COUNT = Integer.getInteger("thread.count", 1);

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
