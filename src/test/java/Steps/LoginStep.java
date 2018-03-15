package Steps;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import Base.BaseUtil;
import Pages.TrefiMainPage;
import Pages.SecureTrefiLoginPage;

public class LoginStep extends BaseUtil {

  private BaseUtil base;
  // private static final String TrefiMainHomePage = "http://www.trefi.com";
  private static final String SecureTrefiPage = "https://secure.trefi.com/auth/";
  private static final int Wait2secs = 2000; // 2000 Milliseconds

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
//    base.driver.navigate().to(SecureTrefiPage);
	  System.out.println("en given");
    base.driver.navigate().to("www.google.com");
    Thread.sleep(Wait2secs);
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

    System.out.println("Username: " + UserName);
    System.out.println("Password: " + Password);
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
      // System.out.println(PageSecureTrefi.getLblLoginFailed());
      // Si llega aqui es que el mensaje de error existia -> Login incorrecto
      PageSecureTrefi.clickLogInBtn();
      assertEquals(PageSecureTrefi.getErrorText(), "Sign in failed, please try again",
          "No error login text. Login succesful");
      sleep(Wait2secs);
    } catch (Exception e) {
      // Si entra aqui es que no hay mensaje de error -> Login correcto
      // e.printStackTrace();
      System.out.println("Login correcto");
      Thread.sleep(Wait2secs);
      // PageSecureTrefi.clickLogoutLnk();
      // Thread.sleep(Wait2secs);
    }
  }

}
