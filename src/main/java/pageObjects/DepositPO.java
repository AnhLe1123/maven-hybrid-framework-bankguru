package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class DepositPO extends BasePage {
    WebDriver driver;

    public DepositPO(WebDriver driver) {
        this.driver = driver;
    }
}
