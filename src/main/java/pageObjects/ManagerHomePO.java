package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.ManageHomePageUI;

import java.nio.charset.Charset;

public class ManagerHomePO extends BasePage {
    WebDriver driver;

    public ManagerHomePO(WebDriver driver) {
        this.driver = driver;
    }

    public String getWelcomeText() {
        waitForElementVisible(driver, ManageHomePageUI.WELCOME_TITLE);
        return getElementText(driver, ManageHomePageUI.WELCOME_TITLE);
    }

    public String getMangerID() {
        waitForElementVisible(driver, ManageHomePageUI.MANGER_ID);
        return getElementText(driver, ManageHomePageUI.MANGER_ID);
    }
}
