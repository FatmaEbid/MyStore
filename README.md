## Lighthouse Utility Integration Guide

This guide explains how to set up and use the `LighthouseUtility` class in your Selenium-Java automation project. It covers:

1. Prerequisites and installation
2. Project configuration
3. Running from the command line
4. Using in test cases directly
5. Executing via TestNG XML suite
6. Configuring flags and official references
7. Displaying results in Allure reports

---

### 1. Prerequisites and Installation

Before using the utility, ensure you have the following installed on your machine:

* **Java JDK 8+**
* **Maven or Gradle** for project build management
* **Google Chrome** (for Lighthouse audits)
* **Node.js (v14+)**
* **Lighthouse CLI** (install globally):
  ```bash
  npm install npm
  npm -v     // to know the npm version
  npm install -g lighthouse
  ```

<br/>

### 2. Project Configuration

1. **Add dependencies** in your `pom.xml` (Maven) or `build.gradle` (Gradle):

   * Selenium WebDriver
   * Allure Java TestNG / JUnit integration

2. **Include the utility package** in your project structure:

   ```text
   src/main/java/Utilities/A11y/lighthouse/
       ├── LighthouseUtility.java
      
   ```

3. **Ensure `lighthouse-reports` directory** is at your project root (it will be auto-created by the utility).

<br/>

### 3. Running from the Command Line

You can manually execute Lighthouse audits to verify your setup:

```bash
lighthouse https://www.example.com \
  --only-categories=accessibility,performance \
  --output=html,json \
  --output-path=./lighthouse-reports/report.html,./lighthouse-reports/report.json \
  --chrome-flags="--headless --no-sandbox" \
  --disable-storage-reset \
  --quiet
```
<br/>

### 4. Using in Test Cases Directly

At the end (or beginning) of any test method, call the helper method:

```java
// Single-line invocation within your test
LighthouseUtility.runAuditForCurrentUrl(driver);
```

This will:

* Extract the current URL from the WebDriver instance
* Run Lighthouse for Accessibility & Performance
* Generate HTML (and JSON if configured) reports
* Open the HTML report in your default browser
* Attach the report(s) to Allure

<br/>

### 5. Executing via TestNG XML Suite

To include Lighthouse at the suite level, add a custom listener or utility call in an `<after-suite>` method. Example TestNG XML snippet:

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="FullRegressionSuite">
  <listeners>
    <listener class-name="Utilities.A11y.lighthouse.LighthouseListener" />
  </listeners>
  <test name="UI Tests">
    <classes>
      <class name="tests.ui.CreateAccountTest" />
      <!-- other test classes -->
    </classes>
  </test>
</suite>
```

In `LighthouseListener`, implement `onFinish(ISuite suite)` to call:

```java
LighthouseUtility.runAuditForCurrentUrl(driver);
LogUtilities.info("✅Lighthouse audit completed for: " + driver.getCurrentUrl());
```

<br/>

### 6. Configuring Flags & Official References

* **Official Lighthouse CLI Flags**:
  * Categories: `--only-categories=<list>` (e.g., `accessibility,performance`)
  * Output: `--output=<html|json|csv>`
  * Path: `--output-path=<path1>[,<path2>]`
  * Chrome flags: `--chrome-flags="--headless --no-sandbox"`
  * Quiet mode: `--quiet`

Add any additional flags in the `LighthouseUtility` builder as needed.

<br/>

### 7. Displaying Results in Allure Reports

The utility attaches each report file to Allure using:

```java
Allure.addAttachment("html report", new FileInputStream(htmlReportFile));
Allure.addAttachment("json report", new FileInputStream(jsonReportFile));
```

In the Allure UI, these attachments appear under the **`Attachments`** section for each test. You can click the HTML file link to open it directly from the report.

---
Good luck, and happy testing! 🚀
