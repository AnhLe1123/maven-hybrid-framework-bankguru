package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class NewCustomerPO extends BasePage {
    WebDriver driver;

    public NewCustomerPO(WebDriver driver) {
        this.driver = driver;
    }
}
