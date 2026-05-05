package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
    features  = { ".\\src\\test\\java\\FeatureFiles\\" },
    glue      = { "stepDefinitions" },
    dryRun    = false,
    plugin    = { "pretty" },
    monochrome = true,
    tags       = "@regression"
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        String base = ExecutionContext.getReportFolder() + "/regression";
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:" + base + "/cucumber.html," +
                "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }

    @Override
    @org.testng.annotations.DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
