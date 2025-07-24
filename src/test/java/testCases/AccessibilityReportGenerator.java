package testCases;

import com.deque.axe.AXE;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class AccessibilityReportGenerator {

	private static final String TEMPLATE_PATH = "templates/accessibility-report-template.html";
	private static final String REPORT_DIR = "a11yReport/";

	public static void generateReport(WebDriver driver, WCAGLevel level) {
		try {
			JSONObject responseJSON = runAxeAnalysis(driver);
			JSONArray violations = responseJSON.getJSONArray("violations");

			List<JSONObject> filtered = filterByLevel(violations, level);

			String htmlTemplate = loadTemplate(TEMPLATE_PATH);
			String timestamp = new SimpleDateFormat("dd-MM-yyy_HH-mm-ss").format(new Date());
			String reportName = "AccessibilityReport_" + timestamp + ".html";
			String outputPath = REPORT_DIR + reportName;

			int errorCount = (int) filtered.stream()
					.filter(v -> getImpact(v).equals("critical") || getImpact(v).equals("serious"))
					.count();
			int warningCount = (int) filtered.stream()
					.filter(v -> getImpact(v).equals("moderate"))
					.count();
			int noticeCount = (int) filtered.stream()
					.filter(v -> getImpact(v).equals("minor"))
					.count();

			String issuesHtml = buildIssuesSection(filtered);

			htmlTemplate = htmlTemplate.replace("{{ERROR_COUNT}}", String.valueOf(errorCount))
					.replace("{{WARNING_COUNT}}", String.valueOf(warningCount))
					.replace("{{NOTICE_COUNT}}", String.valueOf(noticeCount))
					.replace("{{ISSUES_SECTION}}", issuesHtml)
					.replace("{{WCAG_LEVEL}}", level.name())
					.replace("{{URL}}", driver.getCurrentUrl())
					.replace("{{TIMESTAMP}}", timestamp)
					.replace("{{BROWSER}}", "Chrome") // You can dynamically set if needed
					.replace("{{DEVICE}}", "Desktop") // Adjust accordingly
					.replace("{{URL_COUNT}}", "1")
					.replace("{{LOGO_BASE64}}", ""); // Optional logo

			writeFile(outputPath, htmlTemplate);
			openReport(outputPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static JSONObject runAxeAnalysis(WebDriver driver) {
		AXE.Builder builder = new AXE.Builder(driver, getAxeScript());
		return builder.analyze();
	}

	private static URL getAxeScript() {
		return AccessibilityReportGenerator.class.getResource("/axe.min.js");
	}

	/** Loads HTML template as a string. */
	private static String loadTemplate(String path) throws IOException {
		InputStream in = AccessibilityReportGenerator.class.getClassLoader().getResourceAsStream(path);
		if (in == null) throw new FileNotFoundException(path + " not found!");
		return new String(in.readAllBytes(), StandardCharsets.UTF_8);
	}

	/** Writes report to file. */
	private static void writeFile(String filePath, String content) throws IOException {
		File dir = new File(REPORT_DIR);
		if (!dir.exists()) dir.mkdirs();
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			out.write(content.getBytes(StandardCharsets.UTF_8));
		}
	}

	/** Opens the report in the default browser (desktop only). */
	private static void openReport(String filePath) throws IOException {
		File htmlFile = new File(filePath);
		java.awt.Desktop.getDesktop().browse(htmlFile.toURI());
	}

	/** Filters violations by WCAG level. */
	private static List<JSONObject> filterByLevel(JSONArray all, WCAGLevel level) {
		Map<String, WCAGLevel> tagLevels = Map.of(
				"wcag111", WCAGLevel.A,
				"wcag121", WCAGLevel.A,
				"wcag131", WCAGLevel.A,
				"wcag211", WCAGLevel.A,
				"wcag212", WCAGLevel.AA,
				"wcag246", WCAGLevel.AAA
				// Add more mappings as needed
		);

		List<JSONObject> result = new ArrayList<>();

		for (int i = 0; i < all.length(); i++) {
			JSONObject v = all.getJSONObject(i);
			JSONArray tags = v.optJSONArray("tags");
			if (tags == null) continue;

			for (int j = 0; j < tags.length(); j++) {
				String tag = tags.getString(j).toLowerCase();
				WCAGLevel tagLevel = tagLevels.get(tag);
				if (tagLevel != null && tagLevel.ordinal() <= level.ordinal()) {
					result.add(v);
					break;
				}
			}
		}
		return result;
	}

	private static String getImpact(JSONObject violation) {
		return violation.optString("impact", "none").toLowerCase();
	}

	private static String buildIssuesSection(List<JSONObject> violations) {
		StringBuilder sb = new StringBuilder();
		for (JSONObject v : violations) {
			String impact = getImpact(v);
			String description = v.optString("description", "");
			String help = v.optString("help", "");
			String helpUrl = v.optString("helpUrl", "");
			String type = impact.equals("minor") ? "notice" : impact.equals("moderate") ? "warning" : "error";
			String wcagTag = v.optJSONArray("tags").optString(0);

			sb.append("<div class='violation-card impact-").append(impact).append("' data-type='").append(type).append("'>")
					.append("<h3>").append(help).append("</h3>")
					.append("<p>").append(description).append("</p>")
					.append("<p><strong>Impact:</strong> ").append(impact)
					.append(" | <strong>WCAG:</strong> ").append(wcagTag).append("</p>")
					.append("<a href='").append(helpUrl).append("' target='_blank' style='color:#4fc3f7'>More Info</a>")
					.append("</div>");
		}
		return sb.toString();
	}



//@@@@@@@@
// Sample AxeResult class structure used for demonstration
class AxeResult {
	private String description;
	private String helpUrl;
	private String impact;
	private List<String> tags;

	// Getters and constructors here
	public String getDescription() { return description; }
	public String getHelpUrl() { return helpUrl; }
	public String getImpact() { return impact; }
	public List<String> getTags() { return tags; }
}

// Enum for WCAG Level
public  enum WCAGLevel{
	A, AA, AAA, WCAG21A, WCAG21AA, WCAG21AAA, HTML, ARIA, BEST_PRACTICE, SECTION508, ACT;
}


// Report Metadata class
class ReportMetadata {
	private String reportTitle;
	private String generatedDate;
	private int urlCount;
	private String testedBy;

	// Getters and constructors
	public String getReportTitle() {
		return reportTitle;
	}

	public String getGeneratedDate() {
		return generatedDate;
	}

	public int getUrlCount() {
		return urlCount;
	}

	public String getTestedBy() {
		return testedBy;
	}

	public ReportMetadata(String reportTitle, String generatedDate, int urlCount, String testedBy) {
		this.reportTitle = reportTitle;
		this.generatedDate = generatedDate;
		this.urlCount = urlCount;
		this.testedBy = testedBy;
	}
}}