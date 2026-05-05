package runner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * Wires RetryAnalyzer into every @Test method at runtime.
 *
 * Registered in each testng-suite XML via:
 *   <listeners>
 *     <listener class-name="runner.RetryListener"/>
 *   </listeners>
 *
 * This avoids adding retryAnalyzer = RetryAnalyzer.class to every runner
 * class annotation individually.
 */
public class RetryListener implements IAnnotationTransformer {

    @Override
    @SuppressWarnings("rawtypes")
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
