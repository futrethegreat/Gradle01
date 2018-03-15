package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TrefiMainPage {

  public TrefiMainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//*[@id=\"menu-item-43\"]/a/span")
  public WebElement lnkSignIn;

  public void clickSignInLnk() {
    // Click Sign In link
    lnkSignIn.click();
  }

}
