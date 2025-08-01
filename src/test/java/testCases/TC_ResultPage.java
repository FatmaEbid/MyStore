package testCases;

import Utilities.A11y.LogUtilities;
import org.testng.annotations.Test;
import pages.IndexPage;

public class TC_ResultPage extends BaseTestcases{

	@Test
	public void verifySortingOptions(){
		LogUtilities.info("Info: Starting Sorting Options Test");
		indexPage.selectOptionsFromMenuBar(IndexPage.menuList.DRESSES);

	}
}
