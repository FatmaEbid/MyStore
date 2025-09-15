package Utilities.A11y;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class AllureReportUtil {
		/**
		 * Generates an Allure report from the specified results directory and outputs it to the specified output directory.
		 *
		 * @param resultsDir The directory containing Allure results (e.g., "allure-results").
		 * @param outputDir  The directory where the Allure report will be generated (e.g., "allure-report").
		 */

		public static void generateAndOpenAllureReport(String resultsDir, String outputDir) {
			String generateCommand = String.format("allure generate %s --clean --single-file -o %s", resultsDir, outputDir);

			try {
				// Generate the report
				Process generateProcess = Runtime.getRuntime().exec(generateCommand);
				generateProcess.waitFor();
				System.out.println("✅ Allure report generated at: " + outputDir);

				// Open the report in the default browser
				openReportInBrowser(outputDir + "/index.html");

			} catch (IOException | InterruptedException e) {
				System.err.println("❌ Failed to generate or open Allure report: " + e.getMessage());
			}
		}

		private static void openReportInBrowser(String reportPath) {
			String os = System.getProperty("os.name").toLowerCase();

			try {
				if (os.contains("win")) {
					Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", reportPath});
				} else if (os.contains("mac")) {
					Runtime.getRuntime().exec(new String[]{"open", reportPath});
				} else if (os.contains("nix") || os.contains("nux")) {
					Runtime.getRuntime().exec(new String[]{"xdg-open", reportPath});
				} else {
					System.err.println("⚠️ Unsupported OS: " + os);
				}
			} catch (IOException e) {
				System.err.println("❌ Failed to open report in browser: " + e.getMessage());
			}
		}

		@Attachment
		public static byte[] attachScreenshot(String screenshotName, WebDriver driver) {
			return ((TakesScreenshot)driver).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
		}
		//Method to add step with screenshot in Allure report
		public static void addStepWithScreenshot(String stepName, WebDriver driver, String screenshot){
			Allure.step(stepName, () -> {
				attachScreenshot(screenshot, driver);
			});
		}

		// Method to write environment info to allure-results/environment.properties
	public static void writeEnvironmentInfo() {
		Properties props = new Properties();
		props.setProperty("os.name", System.getProperty("os.name"));
		props.setProperty("os.version", System.getProperty("os.version"));
		props.setProperty("java.version", System.getProperty("java.version"));
		props.setProperty("user.name", System.getProperty("user.name"));
		props.setProperty("user.language", System.getProperty("user.language"));
		props.setProperty("user.timezone", System.getProperty("user.timezone"));
		props.setProperty("Browser", "chrome"); // Example version



		try (FileWriter writer = new FileWriter("allure-results/environment.properties")) {
			props.store(writer, "Environment Info");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void backupReport(String sourceDir, String backupBaseDir) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
		Path sourcePath = Paths.get(sourceDir);
		Path backupPath = Paths.get(backupBaseDir, "allure-report-backup-" + timestamp);

		try {
			Files.createDirectories(backupPath);
			Files.walk(sourcePath)
					.forEach(source -> {
						try {
							Path destination = backupPath.resolve(sourcePath.relativize(source));
							if (Files.isDirectory(source)) {
								Files.createDirectories(destination);
							} else {
								Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
			System.out.println("Backup created at: " + backupPath);
		} catch (IOException e) {
			System.err.println("Failed to create backup: " + e.getMessage());
		}
	}
}






