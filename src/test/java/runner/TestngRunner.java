package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(features = { ".\\src\\test\\java\\FeatureFiles\\users\\user_management.feature", }, glue = {
        "stepDefinitions" }, dryRun = false, plugin = {
                "pretty" }, monochrome = true, tags = "@TC46"
// Override at runtime: mvn test -Dcucumber.filter.tags="@AWS_TC45"
)
public class TestngRunner extends AbstractTestNGCucumberTests {

    // ── Browser mode ──────────────────────────────────────────────────────────
    // true → headless (CI / background run)
    // false → headed (local debug / observe the browser)
    private static final boolean HEADLESS = false;

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        System.setProperty("test.headless", String.valueOf(HEADLESS));
        String base = ExecutionContext.getReportFolder();
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:" + base + "/cucumber.html," +
                        "testng:" + base + "/testng-report.xml");
        super.setUpClass();
    }
}