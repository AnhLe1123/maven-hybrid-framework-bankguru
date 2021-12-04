package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.BasePageUI;
import pageUIs.LoginPageUI;

public class LoginPO extends BasePage {
    WebDriver driver;

    public LoginPO(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoginPageDisplayed() {
        waitForElementVisible(driver, BasePageUI.BUTTON_BY_VALUE, "LOGIN");
        return isElementDisplayed(driver, BasePageUI.BUTTON_BY_VALUE, "LOGIN");
    }

    public void clickToHereLink() {
        waitForElementClickable(driver, LoginPageUI.HERE_LINK);
        clickToElement(driver, LoginPageUI.HERE_LINK);
    }

    public boolean isAccessDetailsTitleDisplayed() {
        waitForElementVisible(driver, LoginPageUI.ACCESS_DETAILS_TITLE);
        return isElementDisplayed(driver, LoginPageUI.ACCESS_DETAILS_TITLE);
    }
}
