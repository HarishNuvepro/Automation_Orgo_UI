package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Run via Maven profile: mvn test -Pusers
 * Run via class filter: mvn test -Dtest=UserModuleRunner
 *
 * The @Listeners annotation wires up all suite-level concerns (report folder,
 * retry, parallel thread count) so that either invocation works identically
 * without requiring a TestNG suite XML.
 */
@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        UserModuleRunner.ParallelDataProviderConfig.class
})
@CucumberOptions(features = { ".\\src\\test\\java\\FeatureFiles\\users\\" }, glue = {
        "stepDefinitions" }, dryRun = false, plugin = { "pretty" }, monochrome = true, tags = "@users")
public class UserModuleRunner extends AbstractTestNGCucumberTests {

    /** Caps the data-provider thread pool at 3 for any run of this runner. */
    public static final class ParallelDataProviderConfig implements ISuiteListener {
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
        String base = ExecutionContext.getReportFolder() + "/users";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:" + base + "/cucumber.html," +
                        "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }

    @Override
    @org.testng.annotations.DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
