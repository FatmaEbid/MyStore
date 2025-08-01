package Utilities;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class LighthouseUtility {
	private static final String REPORTS_DIR = "lighthouse-reports";

	public static void runAuditForCurrentUrl(WebDriver driver, LighthouseCategory ...categories) {
		String currentUrl = driver.getCurrentUrl();
		runLighthouseAudit(
				currentUrl,
				List.of(LighthouseCategory.ACCESSIBILITY, LighthouseCategory.PERFORMANCE),
				List.of(LighthouseOutputFormat.HTML)
		);
	}

	public static Map<LighthouseOutputFormat, String> runLighthouseAudit(
			String url,
			//String reportBaseName,
			List<LighthouseCategory> categories,
			List<LighthouseOutputFormat> outputFormats
	) {
		Map<LighthouseOutputFormat, String> generatedReports = new HashMap<>();

		try {
			// ✅ Validate input
			if (url == null || url.trim().isEmpty()) throw new IllegalArgumentException("URL cannot be null or empty.");
			if (categories == null || categories.isEmpty()) throw new IllegalArgumentException("At least one category is required.");
			if (outputFormats == null || outputFormats.isEmpty()) throw new IllegalArgumentException("At least one output format is required.");

			// ✅ Create reports directory if needed
			File reportDir = new File(REPORTS_DIR);
			if (!reportDir.exists()) reportDir.mkdirs();

			String timestamp = new SimpleDateFormat("dd-MM-yyyy  HH-mm-ss").format(new Date());

			// ✅ Build command
			List<String> command = new ArrayList<>();
			command.add("lighthouse");
			command.add(url);

			// ✅ Add categories
			String joinedCategories = categories.stream()
					.map(LighthouseCategory::getValue)
					.collect(Collectors.joining(","));
			command.add("--only-categories=" + joinedCategories);

			// ✅ Add output formats and paths
			outputFormats = outputFormats.stream().distinct().collect(Collectors.toList()); // ✅ remove duplicates

			List<String> outputFormatsList = new ArrayList<>();
			List<String> outputPathsList = new ArrayList<>();

			for (LighthouseOutputFormat format : outputFormats) {
				String formatStr = format.getFormat(); // e.g., "html"
				String reportPath = REPORTS_DIR + "/" + "LightHouseReport" + "-" + timestamp + "." + formatStr;

				outputFormatsList.add(formatStr);
				outputPathsList.add(reportPath);

				generatedReports.put(format, reportPath);
			}




			// ✅ Add both formats and paths to the command
			command.add("--quiet"); // that reduces logs to only critical issues
			command.add("--output=" + String.join(",", outputFormatsList));
			command.add("--output-path=" + String.join(",", outputPathsList));
			command.add("--chrome-flags=--headless"); // Run Chrome in headless mode
			command.add("--disable-extensions"); // Disable extensions for better performance
			command.add("--no-sandbox"); // Disable sandboxing for better performance
			command.add("--disable-storage-reset"); // Disable storage reset to avoid clearing cookies and local storage
			command.add("--disable-network-emulation"); // Disable network emulation for better performance
			command.add("--disable-device-emulation"); // Disable device emulation for better performance

			// ✅ Debug output
			System.out.println("✅ Output formats: " + outputFormatsList);
			System.out.println("✅ Output paths: " + outputPathsList);
			System.out.println("Running Lighthouse Command:");
			System.out.println(String.join(" ", command));

			// ✅ Run Lighthouse process
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			// ✅ Capture Lighthouse CLI output
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println("[Lighthouse] " + line);
				}
			}

			int exitCode = process.waitFor();
			if (exitCode != 0) {
				System.err.println("❌ Lighthouse failed with exit code: " + exitCode);
				return Collections.emptyMap();
			}

			// ✅ Open and attach reports
			for (Map.Entry<LighthouseOutputFormat, String> entry : generatedReports.entrySet()) {
				File file = new File(entry.getValue());
				if (entry.getKey() == LighthouseOutputFormat.HTML) {
					openInBrowser(file);
				}
				attachToAllure(entry.getKey().name().toLowerCase() + " report", file);
			}

			return generatedReports;

		} catch (Exception e) {
			System.err.println("❌ Error running Lighthouse: " + e.getMessage());
			e.printStackTrace(); // Print full exception for debugging
			return Collections.emptyMap();
		}
	}

	private static void openInBrowser(File file) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(file.toURI());
				System.out.println("✅ Opened Lighthouse report in browser: " + file.getAbsolutePath());
			}
		} catch (IOException e) {
			System.err.println("⚠️ Could not open report in browser: " + e.getMessage());
		}
	}

	private static void attachToAllure(String name, File file) {
		try (InputStream is = new FileInputStream(file)) {
			Allure.addAttachment(name, is);
			System.out.println("✅ Attached report to Allure: " + file.getName());
		} catch (IOException e) {
			System.err.println("⚠️ Failed to attach report to Allure: " + e.getMessage());
		}
	}
	public enum LighthouseOutputFormat {
		HTML("html"),
		JSON("json");

		private final String format;

		LighthouseOutputFormat(String format) {
			this.format = format;
		}

		public String getFormat() {
			return format;
		}
	}
	public enum LighthouseCategory {
		ACCESSIBILITY("accessibility"),
		PERFORMANCE("performance"),
		SEO("seo"),
		BEST_PRACTICES("best-practices"),
		PWA("pwa");

		private final String value;

		LighthouseCategory(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
