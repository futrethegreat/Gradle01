package Steps;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ctc.Utils;

import Base.BaseUtil;
import Pages.SecureTrefiLoginPage;
import Pages.TrefiMainPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class LoginStep extends BaseUtil {

	private BaseUtil base;
	// public final String TrefiMainHomePage = "http://www.trefi.com";
	public final String SecureTrefiPage = "https://secure.trefi.com/auth/";
	public final int Wait2secs = 2000; // 2000 Milliseconds

	public LoginStep(BaseUtil base) {
		this.base = base;
	}

	// @Given("^I navigate to the main Trefi page$")
	// public void givenINavigateToTheMainTrefiPage() throws Throwable {
	// base.driver.navigate().to(TrefiMainHomePage);
	// Thread.sleep(Wait2secs);
	// }

	@Given("^I navigate to the secure Trefi page$")
	public void givenINavigateToTheSecureTrefiPage() throws Throwable {

		Utils.consoleMsg("*** ppio Given ***");
		base.driver.get(SecureTrefiPage);
		Utils.consoleMsg("*** despues get page ***");
		waitUntil_isPresent(base.driver, By.name("signinid"));

		Utils.consoleMsg("*** Esta presente ***");
		Utils.consoleMsg(
				"1: " + base.driver.findElement(By.name("enrollform")).findElement(By.name("signinid")).getTagName());

		// base.driver.get("http://www.executeautomation.com/demosite/Login.html");
		// Utils.consoleMsg("1: " +
		// base.driver.findElement(By.name("Login")).findElement(By.name("Login")).getTagName());
		// Utils.consoleMsg("2: " +
		// base.driver.findElement(By.name("Login")).getTagName());

		Utils.consoleMsg("*** GIVEN ***");

	}

	@And("^I click on Sign In link")
	public void andIClickOnSignInLink() throws Throwable {
		TrefiMainPage PageTrefi = new TrefiMainPage(base.driver);
		PageTrefi.clickSignInLnk();
		Thread.sleep(Wait2secs);
		Thread.sleep(Wait2secs);
	}

	@And("^On Secure Trefi Page I enter ([^\"]*) and ([^\\\"]*)$")
	public void andIEnterCredentials(String UserName, String Password) throws Throwable {
		SecureTrefiLoginPage PageSecureTrefi = new SecureTrefiLoginPage(base.driver);
		PageSecureTrefi.Login(UserName, Password);

		Utils.consoleMsg("Username: " + UserName);
		Utils.consoleMsg("Password: " + Password);
		Thread.sleep(Wait2secs);
	}

	@And("^I click Login button$")
	public void andIClickLoginButton() throws Throwable {
		SecureTrefiLoginPage PageSecureTrefi = new SecureTrefiLoginPage(base.driver);
		PageSecureTrefi.clickLogInBtn();
	}

	@Then("^I should see the Tool main page$")
	public void thenIShouldSeeTheToolMainPage() throws Throwable {
		SecureTrefiLoginPage PageSecureTrefi = new SecureTrefiLoginPage(base.driver);
		try {
			// Functions.consoleMsg(PageSecureTrefi.getLblLoginFailed());
			// Si llega aqui es que el mensaje de error existia -> Login incorrecto
			PageSecureTrefi.clickLogInBtn();
			assertEquals(PageSecureTrefi.getErrorText(), "Sign in failed, please try again",
					"No error login text. Login succesful");
			sleep(Wait2secs);
		} catch (Exception e) {
			// Si entra aqui es que no hay mensaje de error -> Login correcto
			// e.printStackTrace();
			Utils.consoleMsg("Login correcto");
			Thread.sleep(Wait2secs);
			// PageSecureTrefi.clickLogoutLnk();
			// Thread.sleep(Wait2secs);
		}
	}

	public boolean waitUntil_isPresent(final WebDriver driver, final By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (TimeoutException te) {
			te.printStackTrace();
			return false;
		}
		return true;
	}

}
