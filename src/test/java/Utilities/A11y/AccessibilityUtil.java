package Utilities.A11y;


import com.deque.axe.AXE;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.WebDriver;


public class AccessibilityUtil {
	// This method runs the Axe analysis and generates an HTML report
	public class HtmlCsRunner {
    // this will get the axe.min.js file from the resources folder, // which is required for the Axe analysis
		private static final URL AXE_SCRIPT_URL = HtmlCsRunner.class.getResource("/axe.min.js");

		// This method analyzes the current page using Axe and generates an HTML report
		public static void analyzePage(WebDriver driver, String htmlReportPath, WCAGLevel level) {
			if (AXE_SCRIPT_URL == null) {
				throw new RuntimeException("axe.min.js not found in resources.");
			}
			// 1. Run the Axe analysis first
			JSONObject result = new AXE.Builder(driver, AXE_SCRIPT_URL).analyze();

// 2. Get all violations
			JSONArray allViolations = result.getJSONArray("violations");

  // 3. Filter violations based on selected WCAG level
			System.out.println("🔍 Detected violations before filtering: " + allViolations.length());

			JSONArray filteredViolations = new JSONArray();
			for (int i = 0; i < allViolations.length(); i++) {
				JSONObject violation = allViolations.getJSONObject(i);
				JSONArray tags = violation.getJSONArray("tags");
				System.out.println("Violation: " + violation.getString("id"));
				System.out.println("Tags: " + violation.getJSONArray("tags").toString());

				for (int j = 0; j < tags.length(); j++) {
					String tag = tags.getString(j);
					if (tag.equalsIgnoreCase(level.getLabel().toLowerCase())) { // Check if the tag matches the selected level, level.getLabel() returns the label of the WCAG level
						filteredViolations.put(violation);
						break;
					}
				}
			}

			// 4. Replace the original violations with the filtered ones
			System.out.println("Axe script URL: " + AXE_SCRIPT_URL); // Debugging line to check if the URL is correct

			result.put("violations", filteredViolations);

			// 5. Get current page URL
			String currentUrl = driver.getCurrentUrl();

			// 6. Generate the HTML report
			generateHtmlReport(result, htmlReportPath, currentUrl, level);

			// 7. Console output
			if (filteredViolations.length() == 0) {
				System.out.println("✅ No accessibility violations found for level " + level.name());
			} else {
				System.out.println("❌ Violations found (" + filteredViolations.length() + ") for level " + level.name());
			}

			generateHtmlReport(result, htmlReportPath,currentUrl, WCAGLevel.AA);
		}


		private static void generateHtmlReport(JSONObject result, String outputPath, String pageUrl, WCAGLevel level) {
			JSONArray violations = result.getJSONArray("violations");

			 String osName = System.getProperty("os.name"); // Get the operating system name
			 String osArch = System.getProperty("os.arch"); // Get the operating system architecture
			String userName = System.getProperty("user.name"); // Get the user name
			String javaVersion = System.getProperty("java.version"); // Get the Java version
			String browserName = "Chrome"; // Default browser name, TODO  still need to implement dynamic browser detection


			String timestamp = java.time.LocalDateTime.now().toString();
			int errorCount = violations.length(); // Only errors shown here
			int warningCount = 0; // Adjust if you categorize separately
			int noticeCount = 0; // You can extend the tool to support these

			StringBuilder html = new StringBuilder();
			// Build the HTML report
			// Use StringBuilder for efficient string concatenation and append the HTML content
			html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
					.append("<title>Accessibility Report</title>")
					.append("<style>")
					.append("body { font-family: Arial, sans-serif; background: #2c2c2c; color: #f0f0f0; padding: 20px; }")
					.append("h1, h2, h3 { color: #f9a825; }")
					.append("h1{color: #f9a825; text-align: center; font-size: 2.5em;}")
					.append(".summary { background: #333; padding: 20px; border-radius: 10px; margin-bottom: 20px; }")
					.append(".stats { display: flex; gap: 20px; }")
					.append(".stat { flex: 1; text-align: center; padding: 20px; border-radius: 10px; }")
					.append(".errors { background: #e53935; }")
					.append(".warnings { background: #fdd835; color: #000; }")
					.append(".notices { background: #29b6f6; }")
					.append(".issue { background: #424242; margin-bottom: 20px; padding: 15px; border-left: 6px solid #e53935; border-radius: 6px; }")
					.append("code { background: #616161; padding: 4px 6px; border-radius: 4px; }")
					.append("a { color: #4fc3f7; text-decoration: none; }")
					.append("</style></head><body>");

			html.append("<h1>🦮 IDA Accessibility Report</h1>")
					.append("<div class='summary'>")
					.append("<p><strong>URL:</strong> <a href='").append(pageUrl).append("' target='_blank'>")// Use target='_blank' to open in a new tab
					.append(pageUrl).append("</a></p>")
					.append("<p><strong>Standard:</strong> ").append(level.getLabel()).append("</p>")
					.append("<p><strong>Browser:</strong> ").append(browserName).append("</p>")
					.append("<p><strong>Operating System:</strong> ").append(osName).append(" (").append(osArch).append(")</p>")
					.append("<p><strong>Timestamp:</strong> ").append(timestamp).append("</p>")
					.append("<div <p><strong>Java Version:</strong> ").append(javaVersion).append("</p></div>")
					.append("<p><strong>User:</strong> ").append(userName).append("</p>")
					.append("<div class='stats'>")
					.append("<div class='stat errors'><h2>Errors</h2><p>").append(errorCount).append("</p></div>")
					.append("<div class='stat warnings'><h2>Warnings</h2><p>").append(warningCount).append("</p></div>")
					.append("<div class='stat notices'><h2>Notices</h2><p>").append(noticeCount).append("</p></div>")
					.append("</div></div>");

			html.append("<h2>📋 Issues - Details</h2>");

			if (violations.length() == 0) {
				html.append("<p><strong>No accessibility issues found. 🎉</strong></p>");
			} else {
				for (int i = 0; i < violations.length(); i++) {
					JSONObject violation = violations.getJSONObject(i);
					JSONArray nodes = violation.getJSONArray("nodes");

					// Iterate through each node in the violation
					for (int j = 0; j < nodes.length(); j++) {
						JSONObject node = nodes.getJSONObject(j);

						html.append("<div class='issue'>")
								.append("<p><strong>Code(s):</strong> ").append(violation.optString("id")).append("</p>")
								.append("<p><strong>Technique(s):</strong> ")
								.append("<a href='").append(violation.optString("helpUrl")).append("' target='_blank'>").append(violation.optString("help")).append("</a></p>")
								.append("<p><strong>Tag:</strong> <code>").append(violation.optString("impact", "N/A")).append("</code></p>")
								.append("<p><strong>Message:</strong> ").append(violation.optString("description")).append("</p>")
								.append("<p><strong>Element:</strong> <code>").append(node.optString("html").replace("<", "&lt;").replace(">", "&gt;")).append("</code></p>")
								//.append(escapeHtml(node.optString("html"))) // Escape HTML to prevent XSS

								.append("</div>");
					}
				}
			}

			html.append("</body></html>");

			try (FileWriter writer = new FileWriter(outputPath)) {
				writer.write(html.toString());
				System.out.println("✅ HTML Accessibility Report saved to: " + outputPath);
			} catch (IOException e) {
				System.err.println("⚠️ Error writing HTML report: " + e.getMessage());
			}
		}

	}
	public  enum WCAGLevel {
		A("WCAG2A"),
		AA("WCAG2AA"),
		AAA("WCAG2AAA"),
		WCAG21A("WCAG21A"),
		WCAG21AA("WCAG21AA"),
		WCAG21AAA("WCAG21AAA"),
		HTML("HTML"), //Issues related to HTML structure and semantics
		ARIA("ARIA"); //Issues related to ARIA (Accessible Rich Internet Applications) attributes



		private final String label;

		WCAGLevel(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}
	private static String escapeHtml(String input) {
		return input.replace("<", "&lt;").replace(">", "&gt;");
	}



}
