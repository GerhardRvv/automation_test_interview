package automation_test_interview;

import java.util.concurrent.TimeUnit;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

/*
 * Requirement:
 * 
 *  1. please fix all the bug in this file 
 *  2. please run it
 *  3. sent your test result and the fixed the file to us
 * 
 * 
 * */

public class GoogleExampleIT {
	
	private static final Logger LOGGER = LogManager.getLogger(GoogleExampleIT.class.getName());
	private WebDriver driver;
	@FindBy(css = "[name='q']")
	private WebElement searchBar;

	@Test
	@Parameters({"searchFor"}) //Get both strings to search from TestSuite.xml 
	public void googleCheeseExample(String searchFor) throws Exception {
		
		LOGGER.info("Page before search: " + driver.getTitle());
		searchBar.clear();
		searchBar.sendKeys(searchFor);
		LOGGER.info("Search for: " + searchFor);
		searchBar.sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains(searchFor));
		LOGGER.info("Page title is: " + driver.getTitle());
		Assert.assertTrue(driver.getTitle().contains(searchFor));
	}
	
/* No longer needed 
	@Test
	@Parameters({"search2"})
	public void googleMilkExample(String search2) throws Exception {
		actualTitle = driver.getTitle();
		searchBar.clear();
		searchBar.sendKeys(search2);
		searchBar.sendKeys(Keys.ENTER);
		Assert.assertTrue(driver.getTitle().equals(actualTitle));
		LOGGER.info("Page title is: " + driver.getTitle());
	}
*/
	
	@BeforeMethod
	@Parameters({"ffProfile", "url" }) //Get fireFoxProfile name and url from TestSuite.xml
	public void setUp(String ffProfile, String url) {
		
		DOMConfigurator.configure("log4j.xml");
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile myprofile = profile.getProfile(ffProfile);
		driver = new FirefoxDriver(myprofile); 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 60), this);
		driver.get(url);
		LOGGER.info("Original url: " + url);
		
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
