package Steps;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import Base.BaseUtil;

/**
 * @author DavidSauce
 *
 */
public class Hook extends BaseUtil {

  private BaseUtil base;
  // private static final String SearchTermsFile = "C:\\Libs\\SearchTerms.xls";
  private static final String ChromeDriverFile = "C:\\Libs\\chromedriver.exe";
  private static final String ChromeDriverType = "webdriver.chrome.driver";
  private static final String FirefoxDriverFile = System.getProperty("user.dir") + "/src/resources/geckodriver";
//  private static final String FirefoxDriverFile = "C:\\Libs\\geckodriver.exe";
  private static final String FirefoxDriverType = "webdriver.gecko.driver";
  private static final String BROWSER = "FF"; // Options can be CH FF IE SF OP

  public Hook(BaseUtil base) {
    this.base = base;
  }

  @Before
  public void InitializeTest() {
    System.out.println("Opening browser: " + BROWSER);
    switch (BROWSER) {
    case "CH":
      base.driver = openChrome();
      break;
    case "FF":
      base.driver = openFirefox();
      System.out.println("Opened FF");
      break;
    default:
      base.driver = openFirefox();
    }
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

    // Get OS name.
    String os = System.getProperty("os.name").toLowerCase();
    System.out.println("OS = " + os + ", Browser = " + browserName + " " + browserVersion);

    base.driver.quit();
  }

  private WebDriver openChrome() {
    // Passing the real Chrome webdriver
    System.out.println("Opening the browser : Chrome");
    setBrowserProperty(ChromeDriverType, ChromeDriverFile);
    // driver se puede usar en LoginStep por dependency injection usando
    // picocontainer
    // (hook y loginstep extienden base)
    return new ChromeDriver();
  }

  private WebDriver openFirefox() {
    // Passing the real Firefox webdriver
    System.out.println("Opening the browser : Firefox");
    setBrowserProperty(FirefoxDriverType, FirefoxDriverFile);
    System.out.println("Opened: Firefox");
    // driver se puede usar en LoginStep por dependency injection usando
    // picocontainer
    // (hook y loginstep extienden base)
    return new FirefoxDriver();
  }

  private void setBrowserProperty(String driverType, String driverLocation) {
    System.setProperty(driverType, driverLocation);
  }

}