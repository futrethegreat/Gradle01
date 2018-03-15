package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SecureTrefiLoginPage {

  public SecureTrefiLoginPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }

  @FindBy(id = "user-name")
  public WebElement txtUserName;
  @FindBy(id = "user-pwd")
  public WebElement txtPassword;
  @FindBy(id = "trefi-auth-btn")
  public WebElement btnLogIn;
  @FindBy(xpath = "//*[@id=\"enrollform\"]/div/span")
  public WebElement txtErrorLogIn;

  public void Login(String userName, String password) {
    txtUserName.clear();
    txtPassword.clear();
    txtUserName.sendKeys(userName);
    txtPassword.sendKeys(password);
  }

  public void clickLogInBtn() {
    // Click Login button
    btnLogIn.click();
  }

  public String getErrorText() {
    return txtErrorLogIn.getText().trim();
  }

}
