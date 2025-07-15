package Utilities.A11y;

import org.testng.ISuiteListener;

public class TestSuiteLogger implements ISuiteListener {
	@Override
	public void onStart(org.testng.ISuite suite) {
		LogUtilities.info("Starting test suite: " + suite.getName());
	}

	@Override
	public void onFinish(org.testng.ISuite suite) {
		LogUtilities.info("Finished test suite: " + suite.getName());
	}
}
