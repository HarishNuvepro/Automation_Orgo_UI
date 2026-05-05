package runner;

import Generic_Utility.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class ExecutionSetupListener implements ISuiteListener {

    private static final Logger log = LoggerFactory.getLogger(ExecutionSetupListener.class);

    @Override
    public void onStart(ISuite suite) {
        ExecutionContext.initialize();
        log.info("Suite '{}' — run folder: {}", suite.getName(), ExecutionContext.getRunFolder());
    }

    @Override
    public void onFinish(ISuite suite) {}
}
