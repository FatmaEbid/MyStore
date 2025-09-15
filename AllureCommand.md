*** Using this command to Clean the folder manually before running tests :rm -rf allure-results
*** To generate the report after running tests: allure generate allure-results --clean --single-file -o TestOutput/allure-results
*** Add after suite if you want to open the report automatically after running tests: allure open TestOutput/allure-results
@AfterSuite
public void afterSuite() {
AllureReportUtil.generateAndOpenAllureReport("allure-results", "TestOutput/allure-report");
}
