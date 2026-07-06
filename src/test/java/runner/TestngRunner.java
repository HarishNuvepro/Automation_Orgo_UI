package runner;

import Generic_Utility.ExecutionContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(features = { ".\\src\\test\\java\\FeatureFiles\\labs", }, glue = {
        "stepDefinitions" }, dryRun = false, plugin = {
                "pretty" }, monochrome = true, tags = "@AWS_TC46 or @AWS_TC47 or @AWS_TC48 or @AWS_TC49"
// Default runs the full users suite: user_create.feature (TC1,2,11,12,21,22)
// + user_management.feature (TC3-TC63 + TC_CLEANUP, which now runs last).
// Override at runtime: mvn test -Dcucumber.filter.tags="@TC54" (single TC)
// mvn test -Dcucumber.filter.tags="@regression" (regression only)
)
public class TestngRunner extends AbstractTestNGCucumberTests {

    // ── Browser mode ──────────────────────────────────────────────────────────
    // true → headless (CI / background run)
    // false → headed (local debug / observe the browser)
    private static final boolean HEADLESS = true;

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass() {
        System.setProperty("test.headless", String.valueOf(HEADLESS));
        String base = ExecutionContext.getReportFolder();
        new java.io.File(base).mkdirs();
        System.setProperty("cucumber.plugin",
                "html:" + base + "/cucumber.html," +
                        "testng:" + base + "/testng-report.xml" + "," +
                        "rerun:" + base + "/failed_scenarios.txt");
        super.setUpClass();
    }
}