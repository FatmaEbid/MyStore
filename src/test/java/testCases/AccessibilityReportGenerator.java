package testCases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccessibilityReportGenerator {

	public static void generateHtmlReport(List<AxeResult> allViolations, WcagLevel wcagLevel, ReportMetadata metadata, String templatePath, String outputPath) {
		try {
			// Step 1: Filter violations by WCAG level
			List<AxeResult> filteredViolations = filterByWcagLevel(allViolations, wcagLevel);

			// Step 2: Count issues by type
			Map<String, Long> issueCounts = countIssuesByType(filteredViolations);

			// Step 3: Read HTML template from file
			String htmlTemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

			// Step 4: Replace placeholders in the template
			String populatedHtml = populateHtml(htmlTemplate, filteredViolations, metadata, wcagLevel, issueCounts);

			// Step 5: Write HTML to output file
			Files.write(Paths.get(outputPath), populatedHtml.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<AxeResult> filterByWcagLevel(List<AxeResult> violations, WcagLevel level) {
		return violations.stream()
				.filter(v -> v.getTags().stream().anyMatch(tag -> tag.equalsIgnoreCase("wcag" + level.name().toLowerCase())))
				.collect(Collectors.toList());
	}

	private static Map<String, Long> countIssuesByType(List<AxeResult> violations) {
		return violations.stream()
				.collect(Collectors.groupingBy(AxeResult::getImpact, Collectors.counting()));
	}

	private static String populateHtml(String template, List<AxeResult> violations, ReportMetadata metadata, WcagLevel level, Map<String, Long> issueCounts) {
		// Replace metadata placeholders
		template = template.replace("{{REPORT_TITLE}}", metadata.getReportTitle());
		template = template.replace("{{GENERATED_DATE}}", metadata.getGeneratedDate());
		template = template.replace("{{TESTED_BY}}", metadata.getTestedBy());
		template = template.replace("{{URL_COUNT}}", String.valueOf(metadata.getUrlCount()));
		template = template.replace("{{WCAG_LEVEL}}", level.name());

		// Replace issue counts
		template = template.replace("{{ERROR_COUNT}}", String.valueOf(issueCounts.getOrDefault("critical", 0L)));
		template = template.replace("{{WARNING_COUNT}}", String.valueOf(issueCounts.getOrDefault("serious", 0L)));
		template = template.replace("{{NOTICE_COUNT}}", String.valueOf(issueCounts.getOrDefault("moderate", 0L) + issueCounts.getOrDefault("minor", 0L)));

		// Generate HTML rows for filtered issues
		StringBuilder issuesHtml = new StringBuilder();
		for (AxeResult v : violations) {
			String color = getImpactColor(v.getImpact());
			issuesHtml.append("<div class='issue' style='border-left: 6px solid ").append(color).append(";'>")
					.append("<h4>").append(v.getDescription()).append("</h4>")
					.append("<p><strong>Impact:</strong> ").append(v.getImpact()).append("</p>")
					.append("<p><strong>Help:</strong> <a href='").append(v.getHelpUrl()).append("'>More info</a></p>")
					.append("</div>");
		}

		template = template.replace("{{ISSUES_SECTION}}", issuesHtml.toString());

		// Chart data placeholder (for simplicity now, we'll enhance it next)
		template = template.replace("{{CHART_DATA}}", generateChartJs(issueCounts));

		return template;
	}

	private static String getImpactColor(String impact) {
		switch (impact.toLowerCase()) {
			case "critical": return "#d32f2f";  // Red
			case "serious": return "#fbc02d";  // Yellow
			case "moderate": return "#0288d1"; // Blue
			case "minor": return "#7cb342";    // Green
			default: return "#9e9e9e";           // Grey
		}
	}

	private static String generateChartJs(Map<String, Long> counts) {
		return "const chartData = {\n" +
				"  labels: ['Errors', 'Warnings', 'Notices'],\n" +
				"  datasets: [{\n" +
				"    data: [" + counts.getOrDefault("critical", 0L) + ", " +
				"           " + counts.getOrDefault("serious", 0L) + ", " +
				"           " + (counts.getOrDefault("moderate", 0L) + counts.getOrDefault("minor", 0L)) + "],\n" +
				"    backgroundColor: ['#d32f2f', '#fbc02d', '#0288d1'],\n" +
				"    hoverOffset: 10\n" +
				"  }]\n" +
				"};\n" +
				"const config = { type: 'pie', data: chartData };\n" +
				"new Chart(document.getElementById('summaryChart'), config);";
	}
}

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
enum WcagLevel{
	A, AA, AAA, WCAG21A, WCAG21AA, WCAG21AAA, HTML, ARIA, BEST_PRACTICE, SECTION508, ACT;
}


// Report Metadata class
class ReportMetadata {
	private String reportTitle;
	private String generatedDate;
	private int urlCount;
	private String testedBy;

	// Getters and constructors
	public String getReportTitle() { return reportTitle; }
	public String getGeneratedDate() { return generatedDate; }
	public int getUrlCount() { return urlCount; }
	public String getTestedBy() { return testedBy; }

	public ReportMetadata(String reportTitle, String generatedDate, int urlCount, String testedBy) {
		this.reportTitle = reportTitle;
		this.generatedDate = generatedDate;
		this.urlCount = urlCount;
		this.testedBy = testedBy;
	}
}
