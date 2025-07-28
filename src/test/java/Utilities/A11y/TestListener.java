package Utilities.A11y;

import Utilities.A11y.lighthouse.LighthouseUtility;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static base.BaseClass.driver;

public class TestListener implements ITestListener {
	@Override
	public void onTestStart(ITestResult result) {
		LogUtilities.setTestName(result.getMethod().getMethodName());
		LogUtilities.info("🔹 Starting Test: " + result.getMethod().getMethodName());
		LighthouseUtility.runAuditForCurrentUrl(driver);
	}


	@Override
	public void onTestSuccess(ITestResult result) {
		LogUtilities.info("✅ Test passed");
		LighthouseUtility.runAuditForCurrentUrl(driver);
		LogUtilities.info("✅Lighthouse audit completed for: " + driver.getCurrentUrl());
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
