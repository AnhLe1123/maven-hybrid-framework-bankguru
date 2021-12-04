package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.BasePageUI;
import pageUIs.NewCustomerPageUI;

public class NewCustomerPO extends BasePage {
    WebDriver driver;

    public NewCustomerPO(WebDriver driver) {
        this.driver = driver;
    }

    public void inputBirthDateTextbox(String birthDate, String birthMonth, String birthYear) {
        waitForElementVisible(driver, NewCustomerPageUI.BIRTH_DATE_TEXTBOX);
        if (driver.toString().contains("firefox")) {
            sendkeyToElement(driver, NewCustomerPageUI.BIRTH_DATE_TEXTBOX, birthYear + "-" + birthMonth + "-" + birthDate);
        } else {
            sendkeyToElement(driver, NewCustomerPageUI.BIRTH_DATE_TEXTBOX, birthDate + "-" + birthMonth + "-" + birthYear);
        }
    }

    public void inputToCustomerTextboxByName(String textboxName, String value) {
        waitForElementVisible(driver, BasePageUI.TEXTBOX_BY_NAME, textboxName);
        clickToElement(driver, BasePageUI.TEXTBOX_BY_NAME, textboxName);
        if (driver.toString().contains("edge") || driver.toString().contains("chrome")) {
            sendkeyToElementByJS(driver, BasePageUI.TEXTBOX_BY_NAME, value, textboxName);
        } else {
            sendkeyToElement(driver, BasePageUI.TEXTBOX_BY_NAME, value, textboxName);
        }
    }
}
