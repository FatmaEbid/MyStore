package Utilities.A11y;

import com.deque.axe.AXE;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;

public class AccessUtilities {
	private static final URL AXE_SCRIPT = AccessUtilities.class.getResource("/axe.min.js");

	public static boolean isAxeScriptLoaded(WebDriver driver, EnumSet<WCAGLevel> levels, String reportName) {
		if (AXE_SCRIPT == null) {
			System.err.println("❌ Axe script not found. Ensure axe.min.js is in the resources directory.");
			return false;
		}
		else {
			scanPage(driver, levels,  reportName);
		}
		return true;
	}
	public enum WCAGLevel {
		A("wcag2a"),
		AA("wcag2aa"),
		AAA("wcag2aaa");

		private final String tag;

		WCAGLevel(String tag) {
			this.tag = tag;
		}

		public String getTag() {
			return tag;
		}
	}

	public static boolean scanPage(WebDriver driver, EnumSet<WCAGLevel> levels, String reportName) {
		try {
			// Convert WCAG levels to String tags
			String[] tags = levels.stream().map(WCAGLevel::getTag).toArray(String[]::new);

			JSONObject responseJSON = new AXE.Builder(driver, AXE_SCRIPT)
					.include("html")
					.analyze();

			JSONArray violations = responseJSON.getJSONArray("violations");

			if (violations.length() == 0) {
				System.out.println("✅ No accessibility violations found.");
				return true;
			} else {
				System.out.println("\n❌ Accessibility Violations Found: " + violations.length());
				for (int i = 0; i < violations.length(); i++) {
					JSONObject violation = violations.getJSONObject(i);
					System.out.println("------------------------------------------------");
					System.out.println("🔹 Rule: " + violation.getString("help"));
					System.out.println("🔸 Impact: " + violation.getString("impact"));
					System.out.println("🔍 Description: " + violation.getString("description"));
					System.out.println("🔗 Help URL: " + violation.getString("helpUrl"));
					JSONArray nodes = violation.getJSONArray("nodes");
					System.out.println("📍 Affected Elements: " + nodes.length());
					for (int j = 0; j < nodes.length(); j++) {
						JSONObject node = nodes.getJSONObject(j);
						System.out.println("   - " + node.getJSONArray("target"));
					}
				}

				// Optional: Write full report as JSON or HTML
				writeHtmlReport(responseJSON, reportName);
				return false; // test failed due to violations
			}
		} catch (Exception e) {
			System.err.println("🚨 Error during accessibility scan: " + e.getMessage());
			return false;
		}
	}

	private static void writeHtmlReport(JSONObject json, String fileName) {
		File report = new File("accessibility-reports/" + fileName + ".html");
		report.getParentFile().mkdirs(); // ensure directory exists

		try (FileWriter writer = new FileWriter(report)) {
			writer.write("<html><head><title>Accessibility Report</title></head><body>");
			writer.write("<h2>Accessibility Violations Report</h2>");
			writer.write("<pre>" + json.toString(2) + "</pre>");
			writer.write("</body></html>");
			System.out.println("📄 Report written to: " + report.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("❌ Failed to write report: " + e.getMessage());
		}
	}
}
