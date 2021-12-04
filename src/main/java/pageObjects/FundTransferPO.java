package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class FundTransferPO extends BasePage {
    WebDriver driver;

    public FundTransferPO(WebDriver driver) {
        this.driver = driver;
    }
}
