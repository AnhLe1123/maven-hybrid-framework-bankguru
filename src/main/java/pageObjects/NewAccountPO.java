package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class NewAccountPO extends BasePage {
    WebDriver driver;

    public NewAccountPO(WebDriver driver) {
        this.driver = driver;
    }
}
