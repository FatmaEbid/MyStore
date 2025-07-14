package Utilities.A11y;


import org.testng.IExecutionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureReportListener implements IExecutionListener {
	@Override
	public void onExecutionStart() {
		//Keep historical reports
		try {
			String timestamp = java.time.LocalDateTime.now()
					.toString()
					.replace(":", "-")
					.replace("T", "_")
					.substring(0, 16);

			String reportFolder = "allure-report-" + timestamp;
			System.out.println("Generating Allure Report to: " + reportFolder);

			ProcessBuilder processBuilder = new ProcessBuilder(
					"allure", "generate", "allure-results", "--clean", "-o", reportFolder
			);
			processBuilder.inheritIO();
			Process process = processBuilder.start();
			process.waitFor();
			System.out.println("Allure Report generated successfully at: " + reportFolder);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

		@Override
		public void onExecutionFinish() {
			try {
				System.out.println("Generating Allure Report...");
				ProcessBuilder processBuilder = new ProcessBuilder(
						"allure", "generate", "allure-results", "--clean", "-o", "allure-report"
				);
				processBuilder.inheritIO();
				Process process = processBuilder.start();
				process.waitFor();
				System.out.println("Allure Report generated successfully.");
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


