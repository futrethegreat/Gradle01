package Steps;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ctc.Utils;

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
	// public final String SearchTermsFile = "C:\\Libs\\SearchTerms.xls";
	// public final String ChromeDriverFileLinux = System.getProperty("user.dir") +
	// "/src/resources/chromedriver";
	// public final String ChromeDriverFileWindows = "C:\\Libs\\chromedriver.exe";
	// public final String ChromeDriverType = "webdriver.chrome.driver";
	// public final String FirefoxDriverFileLinux = System.getProperty("user.dir") +
	// "/src/resources/geckodriver";
	// public final String FirefoxDriverFileWindows = "C:\\Libs\\geckodriver.exe";
	// public final String FirefoxDriverType = "webdriver.gecko.driver";
	// public final String BROWSER = "FF"; // Options can be CH FF IE SF OP
	// public final String OperatingSystem =
	// System.getProperty("os.name").toLowerCase();
	// public String DriverFile;
	// public String DriverType;

	public Hook(BaseUtil base) {
		this.base = base;
	}

	@Before
	public void InitializeTest() {
		// if (OperatingSystem.contains("win") && (BROWSER == "FF")) {
		// DriverFile = FirefoxDriverFileWindows;
		// DriverType = FirefoxDriverType;
		// } else if (OperatingSystem.contains("win") && (BROWSER == "CH")) {
		// DriverFile = ChromeDriverFileWindows;
		// DriverType = ChromeDriverType;
		// } else if (OperatingSystem.contains("nux") && (BROWSER == "FF")) {
		// DriverFile = FirefoxDriverFileLinux;
		// DriverType = FirefoxDriverType;
		// } else if (OperatingSystem.contains("nux") && (BROWSER == "CH")) {
		// DriverFile = ChromeDriverFileLinux;
		// DriverType = ChromeDriverType;
		// }
		Utils.setEnvironment();
		base.driver = openBrowser();

		Utils.consoleMsg(
				"Opening browser: " + Utils.BROWSER + " for " + Utils.OperatingSystem.toUpperCase().toString());

		// switch (BROWSER) {
		// case "CH":
		// base.driver = openChrome();
		// break;
		// case "FF":
		// base.driver = openFirefox();
		// Functions.consoleMsg("Opened FF");
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

		Utils.consoleMsg("OS = " + Utils.OperatingSystem.toUpperCase() + ", Browser = " + browserName + " "
				+ browserVersion);

		base.driver.quit();
	}

	private WebDriver openBrowser() {
		// Passing the real webdriver for the browser selected
		Utils.consoleMsg("Before setting up browser driver for browser: " + Utils.BROWSER);

		System.setProperty(Utils.DriverType, Utils.DriverFile);
		// driver se puede usar en LoginStep por dependency injection usando
		// picocontainer
		// (hook y loginstep extienden base)
		if (Utils.BROWSER == "CH") {
			return new ChromeDriver();
		} else if (Utils.BROWSER == "FF") {
			return new FirefoxDriver();
		} else
			return new FirefoxDriver();
	}

	// private WebDriver openChrome() {
	// // Passing the real Chrome webdriver
	// Functions.consoleMsg("Opening the browser : Chrome");
	// setBrowserProperty(ChromeDriverType, ChromeDriverFile);
	// // driver se puede usar en LoginStep por dependency injection usando
	// // picocontainer
	// // (hook y loginstep extienden base)
	// return new ChromeDriver();
	// }
	//
	// private WebDriver openFirefox() {
	// // Passing the real Firefox webdriver
	// Functions.consoleMsg("Opening the browser : Firefox");
	// setBrowserProperty(FirefoxDriverType, FirefoxDriverFile);
	// Functions.consoleMsg("Opened: Firefox");
	// // driver se puede usar en LoginStep por dependency injection usando
	// // picocontainer
	// // (hook y loginstep extienden base)
	// return new FirefoxDriver();
	// }
}