package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({
        ExecutionSetupListener.class,
        RetryListener.class,
        RolesModuleRunner.SequentialDataProviderConfig.class
})
@CucumberOptions(
        features = { ".\\src\\test\\java\\FeatureFiles\\roles\\" },
        glue = { "stepDefinitions" },
        dryRun = false,
        plugin = { "pretty" },
        monochrome = true,
        tags = "@roles"
)
public class RolesModuleRunner extends AbstractTestNGCucumberTests {

    public static final class SequentialDataProviderConfig implements ISuiteListener {
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
        String base = ExecutionContext.getReportFolder() + "/roles";
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
