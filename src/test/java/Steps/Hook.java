package Steps;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import Base.BaseUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * @author DavidSauce
 *
 */
public class Hook extends BaseUtil {

	private BaseUtil base;
	// private static final String SearchTermsFile = "C:\\Libs\\SearchTerms.xls";
	private static final String ChromeDriverFileLinux = System.getProperty("user.dir") + "/src/resources/chromedriver";
	private static final String ChromeDriverFileWindows = "C:\\Libs\\chromedriver.exe";
	private static final String ChromeDriverType = "webdriver.chrome.driver";
	private static final String FirefoxDriverFileLinux = System.getProperty("user.dir") + "/src/resources/geckodriver";
	private static final String FirefoxDriverFileWindows = "C:\\Libs\\geckodriver.exe";
	private static final String FirefoxDriverType = "webdriver.gecko.driver";
	private static final String BROWSER = "FF"; // Options can be CH FF IE SF OP
	private static final String OperatingSystem = System.getProperty("os.name").toLowerCase();
	private static String DriverFile;
	private static String DriverType;

	public Hook(BaseUtil base) {
		this.base = base;
	}

	@Before
	public void InitializeTest() {
		if (OperatingSystem.contains("win") && (BROWSER == "FF")) {
			DriverFile = FirefoxDriverFileWindows;
			DriverType = FirefoxDriverType;
		} else if (OperatingSystem.contains("win") && (BROWSER == "CH")) {
			DriverFile = ChromeDriverFileWindows;
			DriverType = ChromeDriverType;
		} else if (OperatingSystem.contains("nux") && (BROWSER == "FF")) {
			DriverFile = FirefoxDriverFileLinux;
			DriverType = FirefoxDriverType;
		} else if (OperatingSystem.contains("nux") && (BROWSER == "CH")) {
			DriverFile = ChromeDriverFileLinux;
			DriverType = ChromeDriverType;
		}
		base.driver = openBrowser();

		System.out.println("Opening browser: " + BROWSER + " for " + OperatingSystem.toUpperCase().toString());

		// switch (BROWSER) {
		// case "CH":
		// base.driver = openChrome();
		// break;
		// case "FF":
		// base.driver = openFirefox();
		// System.out.println("Opened FF");
		// break;
		// default:
		// base.driver = openFirefox();
		// }
	}

	@After
	public void TearDownTest(Scenario scenario) {
		if (scenario.isFailed()) {
			// Scenario fallido
		}
		// Get Browser name and version.
		Capabilities caps = ((RemoteWebDriver) base.driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();

		System.out
				.println("OS = " + OperatingSystem.toUpperCase() + ", Browser = " + browserName + " " + browserVersion);

		base.driver.quit();
	}

	private WebDriver openBrowser() {
		// Passing the real webdriver for the browser selected
		System.out.println("Before setting up browser driver for browser: " + BROWSER);

		System.setProperty(DriverType, DriverFile);
		// driver se puede usar en LoginStep por dependency injection usando
		// picocontainer
		// (hook y loginstep extienden base)
		if (BROWSER == "CH") {
			return new ChromeDriver();
		} else if (BROWSER == "FF") {
			return new FirefoxDriver();
		} else
			return new FirefoxDriver();
	}

	// private WebDriver openChrome() {
	// // Passing the real Chrome webdriver
	// System.out.println("Opening the browser : Chrome");
	// setBrowserProperty(ChromeDriverType, ChromeDriverFile);
	// // driver se puede usar en LoginStep por dependency injection usando
	// // picocontainer
	// // (hook y loginstep extienden base)
	// return new ChromeDriver();
	// }
	//
	// private WebDriver openFirefox() {
	// // Passing the real Firefox webdriver
	// System.out.println("Opening the browser : Firefox");
	// setBrowserProperty(FirefoxDriverType, FirefoxDriverFile);
	// System.out.println("Opened: Firefox");
	// // driver se puede usar en LoginStep por dependency injection usando
	// // picocontainer
	// // (hook y loginstep extienden base)
	// return new FirefoxDriver();
	// }
}