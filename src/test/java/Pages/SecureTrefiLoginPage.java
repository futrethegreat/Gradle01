package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SecureTrefiLoginPage {

	public SecureTrefiLoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "UserName") // Prueba executeautomation
	// @FindBy(id = "user-name")
	// @FindBy(name = "signinid")
	public WebElement txtUserName;

	@FindBy(name = "Password") // Prueba executeautomation
	// @FindBy(id = "user-pwd")
	// @FindBy(name = "pass")
	public WebElement txtPassword;

	@FindBy(name = "Login") // Prueba executeautomation
	// @FindBy(id = "trefi-auth-btn")
	// @FindBy(name = "submitTrefi")
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
