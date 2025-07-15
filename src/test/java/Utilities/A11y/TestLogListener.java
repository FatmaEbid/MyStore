package Utilities.A11y;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestLogListener implements ITestListener {
	@Override
	public void onTestStart(ITestResult result) {
		LogUtilities.setTestName(result.getMethod().getMethodName());
		LogUtilities.info("🔹 Starting Test: " + result.getMethod().getMethodName());
	}


	@Override
	public void onTestSuccess(ITestResult result) {
		LogUtilities.info("✅ Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		LogUtilities.error("❌ Test failed: " + result.getThrowable().getMessage());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		LogUtilities.warn("⚠️ Test skipped");
	}
}
