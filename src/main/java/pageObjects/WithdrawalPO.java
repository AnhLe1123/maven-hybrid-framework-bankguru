package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class WithdrawalPO extends BasePage {
    WebDriver driver;

    public WithdrawalPO(WebDriver driver) {
        this.driver = driver;
    }
}
